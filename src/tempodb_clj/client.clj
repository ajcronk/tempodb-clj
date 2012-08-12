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

(defn build-querystring-params
    [identifier params]
    (clojure.string/join "&" (for [v params] (str identifier "=" (java.net.URLEncoder/encode (name v))))))

(defn build-querystring-attrs
    [params]
    (clojure.string/join "&" (for [[k v] params] (str "attr[" (name k) "]=" (java.net.URLEncoder/encode (name v))))))

(defn build-querystring
    [id key tag attr]
    (def querystring-ids (build-querystring-params "id" id))
    (def querystring-keys (build-querystring-params "key" key))
    (def querystring-tags (build-querystring-params "tag" tag))
    (def querystring-attrs (build-querystring-attrs attr))
    (str querystring-ids querystring-keys querystring-tags querystring-attrs))

(defn build-full-url
    [client target]
    (def protocol (if (client :secure) 
        "https://"
        "http://"))
    (format "%s%s:%s/%s%s" protocol (client :host) (client :port) constants/+tempodb-api-version+ target))

(defn request
    [client target method & {:keys [body] :or {body nil}}]
    (def http-call
        (condp = method
            "GET" http/get
            "POST" http/post
            "PUT" http/put))

    (def url (build-full-url client target))
    (println url)
    (def result (http-call url 
                    {:basic-auth [(client :key) (client :secret)]
                     :form-params body
                     :content-type :json
                     :as :json  }))
    result)

(defn create-series
    "create a new series with optionally specified key"
    [client & {:keys [key]
        :or {key nil}}]
    (def target "/series/")
    (if key 
        (request client target "POST" :body {:key key})
        (request client target "POST")))
    

(defn get-series
    "return optionally filtered series"
    [client & {:keys [id key tag attr]
        :or {id [] key [] tag [] attr {} }}]

    (def target (format "/series/?%s" (build-querystring id key tag attr)))
    (request client target "GET"))
