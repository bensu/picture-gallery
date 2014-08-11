(ns picture-gallery.handler
  (:require [compojure.core :refer [defroutes routes]]
            [compojure.route :as route]
            [noir.util.middleware :as noir-middleware]
            [picture-gallery.routes.home :refer [home-routes]]
            [picture-gallery.routes.auth :refer [auth-routes]]
            [picture-gallery.routes.upload :refer [upload-routes]]))

(defn init []
  (println "picture-gallery is starting"))

(defn destroy []
  (println "picture-gallery is shutting down"))

(defroutes app-routes
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (noir-middleware/app-handler
     [auth-routes
      home-routes
      upload-routes
      app-routes]))

