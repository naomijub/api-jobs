(ns api-job.handlers.language
  (:require [cheshire.core :as json]
            [clojure.string :as str]
            [clj-http.client :as client]))

(defn edns-to-json [m1 m2]
  (let [merged-map (merge-with into m1 m2)]
    (json/generate-string merged-map)))

(defn language-client [lang]
  (-> (str "https://jobs.github.com/positions.json?search="  lang)
      (client/get)
      (:body)
      (json/parse-string true)
      (vec)))

(defn get-job-keys [jobs]
  (map
   #(select-keys % [:title :company :location :created_at :url])
   jobs))

(defn filtered-by [key-param param jobs]
  (filter
   #(= (.toLowerCase (key-param %))  (.toLowerCase param))
   jobs))

(defn browse-by-lang [lang]
  (let [m1 {:language lang :jobs []}
        m2 {:jobs (get-job-keys (language-client lang))}]
    (edns-to-json m1 m2)))

(defn browse-filtered-by-lang [lang company]
  (let [m1 {:language lang :company company :jobs []}
        jobs (get-job-keys (language-client lang))
        m2 {:jobs (filtered-by :company company jobs)}]
    (edns-to-json m1 m2)))

(defn browse-by-langs [langs]
  (->> langs
       (map (fn [lang]
              {:language lang :jobs (get-job-keys (language-client lang))}))
       (json/generate-string)))
