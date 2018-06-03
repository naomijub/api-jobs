(defproject api-job "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.6.1"]
                 [metosin/compojure-api "1.1.12"]
                 [ring/ring-defaults "0.3.2"]]
  :ring {:handler api-job.handler/app}
  :test-refresh {:growl false
                 :notify-on-success false
                 :quiet true
                 :changes-only true
                 :stack-trace-depth nil
                 :run-once false
                 :watch-dirs ["src" "test"]
                 :refresh-dirs ["src" "test"]})
