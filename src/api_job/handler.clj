(ns api-job.handler
  (:require [compojure.core :refer [defroutes]]
            [compojure.route :as route]
            [compojure.api.sweet :refer [context GET POST]]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [api-job.handlers.language :refer [browse-by-lang
                                               browse-filtered-by-lang
                                               browse-by-langs]]))

(defroutes app-routes
  (context "/jobs" []
    (GET "/" [lang] (response (browse-by-lang lang)))
    (GET "/langs" [lang] (response (browse-by-langs lang)))
    (GET "/filtered" [lang company]
      (response (browse-filtered-by-lang lang company))))
  (route/not-found "Not Found"))

(def app
  (wrap-defaults app-routes site-defaults))
