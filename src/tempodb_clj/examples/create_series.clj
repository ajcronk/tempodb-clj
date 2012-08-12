(ns tempodb-clj.examples.create-series
  (:require [tempodb-clj.client :as tempodb])
  (:gen-class))

(defn -main [& args]
    (def client (tempodb/new-client "myagley" "opensesame"))
    
    (println (tempodb/create-series client))
    ;(println (tempodb/create-series client :key "andy-clj"))
    )
