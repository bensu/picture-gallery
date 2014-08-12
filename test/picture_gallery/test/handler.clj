(ns picture-gallery.test.handler
  (:require [clojure.test :refer :all]
            [ring.mock.request :refer :all]
            [noir.util.crypt :refer [encrypt]]
            [picture-gallery.handler :refer :all]))

(defn mock-get-user [id]
  (if (= id "foo")
    {:id "foo" :pass (encrypt "12345")}))

(deftest test-login
  (testing "Login Success"
    (with-redefs [picture-gallery.models.db/get-user mock-get-user]
      (is
       (-> (request :post "/login" {:id "foo" :pass "12345"})
           app
           :headers
           (get "Set-Cookie")
           not-empty))))

  (testing "Password Mismatch"
    (with-redefs [picture-gallery.models.db/get-user mock-get-user]
      (is
       (-> (request :post "/login" {:id "foo" :pass "123456"})
           app
           :headers
           (get "Set-Cookie")
           empty?))))

  (testing "User Missing"
    (with-redefs [picture-gallery.models.db/get-user mock-get-user]
      (is
       (-> (request :post "/login" {:id "bar" :pass "12345"})
           app
           :headers
           (get "Set-Cookie")
           empty?)))))

;; (deftest test-app
;;   (testing "main route"
;;     (let [response (app (request :get "/"))]
;;       (is (= (:status response) 200))
;;       (is (= (:body response) "Hello World"))))

;;   (testing "not-found route"
;;     (let [response (app (request :get "/invalid"))]
;;       (is (= (:status response) 404)))))
