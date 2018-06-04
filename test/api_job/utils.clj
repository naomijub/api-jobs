(ns api-job.utils)

(defn every-not-empty? [s1 pred]
  (and (not (empty? s1))
       (every? s1 pred)))
