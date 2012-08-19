(ns tempodb-clj.examples.update-series
  (:require [tempodb-clj.client :as tempodb]))

(defn -main [& args]
    (def client (tempodb/new-client "your-api-key" "your-api-secret"))
    
    (let [s (tempodb/create-series client)]
        (tempodb/update-series client (s :id) (s :key) (s :name) ["tag1" "tag2"] {:foo "bar"})))
