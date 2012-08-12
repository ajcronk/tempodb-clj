(ns tempodb-clj.examples.create-series
  (:require [tempodb-clj.client :as tempodb])
  (:gen-class))

(defn -main [& args]
    (def client (tempodb/new-client "your-api-key" "your-api-secret"))
    
    (println (tempodb/create-series client :key "your-custom-series"))
    ;(println (tempodb/create-series client))
    )
