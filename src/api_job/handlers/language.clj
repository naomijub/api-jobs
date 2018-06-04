(ns api-job.handlers.language
  (:require [cheshire.core :as json]
            [clj-http.client :as client]))

(defn edns-to-json [m1 m2]
  (let [merged-map (merge-with into m1 m2)]
    (json/generate-string merged-map)))

(defn language-client [lang]
  (-> (str "https://jobs.github.com/positions.json?search="  "clojure")
      (client/get)
      (:body)
      (json/parse-string true)
      (vec)))

(defn browse-by-lang [lang]
  (let [m1 {:language lang :jobs []}
        m2 {:jobs (language-client lang)}]
    (edns-to-json m1 m2)))
