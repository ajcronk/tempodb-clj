(ns tempodb-clj.client
  (:require
    [clj-http.client :as http]
    [tempodb-clj.constants :as constants])
  (:gen-class))

(defn new-client
    "Holds TempoDB client credentials"
    [key secret & {:keys [host port secure] 
        :or {host constants/+tempodb-api-host+, port constants/+tempodb-api-port+, secure constants/+tempodb-api-secure+}}]
    {:key key, :secret secret, :host host, :port port, :secure secure})

(defn -main [& args]
  (def client (new-client "myagley" "opensesame" :host "staging-api.tempo-db.com"))
  (println client))
  ;(println (:status (http/get "http://tempo-db.com"))))
