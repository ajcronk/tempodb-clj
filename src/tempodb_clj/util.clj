(ns tempodb-clj.util)

(defn in? 
  "true if seq contains elm"
  [seq elm]  
  (some #(= elm %) seq))