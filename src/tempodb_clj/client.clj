(ns tempodb-clj.client
  (:require
    [clj-http.client :as http]
    [tempodb-clj.constants :as constants]
    [tempodb-clj.util :as util])
  (:gen-class))

(defn new-client
    "map of TempoDB client credentials"
    [key secret & {:keys [host port secure] 
        :or {host constants/+tempodb-api-host+, port constants/+tempodb-api-port+, secure constants/+tempodb-api-secure+}}]
    {:key key, :secret secret, :host host, :port port, :secure secure})

(defn build-querystring
    [[identifier params]]
    (if (map? params)
        (clojure.string/join "&" (for [[k v] params] (str (name identifier) "[" (name k) "]=" (java.net.URLEncoder/encode v))))
        (clojure.string/join "&" (for [v params] (str (name identifier) "=" (java.net.URLEncoder/encode v))))))

(defn get-querystring
    [params]
    (reduce str (map build-querystring (seq params))))

(defn build-full-url
    [client target]
    (let [protocol (if (client :secure) "https" "http")]
        (format "%s://%s:%s/%s%s" protocol (client :host) (client :port) constants/+tempodb-api-version+ target)))

(defn request
    [client target method & {:keys [body] :or {body nil}}]
    (let [http-call
            (condp = method
                "GET" http/get
                "POST" http/post
                "PUT" http/put)
          url (build-full-url client target)]
        (println url)
        (http-call url 
                    {:basic-auth [(client :key) (client :secret)]
                     :form-params body
                     :content-type :json
                     :as :json  })))

(defn create-series
    "create a new series with optionally specified key"
    [client & {:keys [key]
        :or {key nil}}]
    (let [target "/series/"
          result 
            (if key 
                (request client target "POST" :body {:key key})
                (request client target "POST"))]
        (result :body)))


(defn get-series
    "return optionally filtered series"
    [client & {:keys [id key tag attr]
        :or {id [] key [] tag [] attr {} }}]

    (let [target (format "/series/?%s" (get-querystring {:id id, :key key, :tag tag, :attr attr}))
          result (request client target "GET")]
        (result :body)))
    


(defn update-series
    [client id key name tags attributes]
    (let [target (format "/series/id/%s/" id)
          body {:id id, :key key, :name name, :tags tags, :attributes attributes}
          result (request client target "PUT" :body body)]
        (result :body)))
