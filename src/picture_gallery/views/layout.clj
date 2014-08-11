(ns picture-gallery.views.layout
  (:require [hiccup.page :refer [html5 include-css include-js]]
            [hiccup.element :refer [link-to]]
            [hiccup.form :refer :all]
            [noir.session :as session]))

(defn base [& content]
  (html5
   [:head
    [:title "Welcome to picture-gallery"]
    (include-css "/css/screen.css")
    (include-js "//code.jquery.com/jquery-2.0.2.min.js")]
   [:body content]))

(defn make-menu [& items]
  [:div (for [item items] [:div.menuitem item])])

(defn guest-menu []
  (make-menu
   (link-to "/" "Home")
   (link-to "/register" "Register")
   (form-to [:post "/login"]
             (text-field {:placeholder "Screen Name"} "id")
             (password-field {:placeholder "Password"} "pass")
             (submit-button "Login"))))

(defn user-menu [user]
  (make-menu
     (link-to "/" "Home")
     (link-to "/upload" "Upload Images")
     (link-to "/logout" (str "Logout " user))))

(defn common [& content]
  (base
   (if-let [user (session/get :user)]
      (user-menu user)
      (guest-menu))
   [:div.content content]))
