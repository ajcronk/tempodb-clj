(ns tempodb-clj.examples.get-querystring
  (:require [tempodb-clj.client :as tempodb])
  (:gen-class))

(defn -main [& args]
    (def client (tempodb/new-client "your-api-key" "your-api-secret"))
    
    (println (tempodb/get-querystring {:foo ["1" "2"] :attr {:wee "4" :whoa "br"}})))
