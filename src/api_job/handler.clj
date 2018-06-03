(ns api-job.handler
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [compojure.api.sweet :refer [context GET POST]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (context "/jobs" []
    (GET "/" [lang] (response {:language lang})))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
