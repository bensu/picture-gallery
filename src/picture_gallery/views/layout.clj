(ns picture-gallery.views.layout
  (:require [hiccup.page :refer [html5 include-css]]
            [hiccup.element :refer [link-to]]
            [hiccup.form :refer :all]
            [noir.session :as session]))

(defn base [& content]
  (html5
   [:head
    [:title "Welcome to picture-gallery"]
    (include-css "/css/screen.css")]
   [:body content]))

(defn guest-menu []
  [:div (link-to "/register" "Register")
   (form-to [:post "/login"]
            (text-field {:placeholder "Screen Name"} "id")
            (password-field {:placeholder "Password"} "pass")
            (submit-button "Login"))])

(defn user-menu [user]
  (list
   [:div (link-to "/upload" "Upload Images")]
   [:div (link-to "/logout" (str "Logout " user))]))

(defn common [& content]
  (base
   (if-let [user (session/get :user)]
      (user-menu user)
      (guest-menu))
   content))
