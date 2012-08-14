(ns tempodb-clj.examples.get-series
  (:require [tempodb-clj.client :as tempodb])
  (:gen-class))

(defn -main [& args]
    (def client (tempodb/new-client "myagley" "opensesame"))
    
    ;(println (tempodb/get-series client))
    ;(println (tempodb/get-series client :id '("0c3a107c53024ea2a81b8115140b6831")))
    (println (tempodb/get-series client :key '("foo" "bar")))
    ;(println (tempodb/get-series client :tag '("tag1" "tag2")))
    ;(println (tempodb/get-series client :attr {:blah "boo"}))
    ;(println (tempodb/get-series client :tag ("tag1") :attr {:blah "boo"}))
    )
