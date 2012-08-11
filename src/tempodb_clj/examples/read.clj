(ns tempodb-clj.examples.read
  (:require [tempodb-clj.client :as tempodb])
  (:gen-class))

(defn -main [& args]
    (def client (tempodb/new-client "myagley" "opensesame"))
    (println client))



