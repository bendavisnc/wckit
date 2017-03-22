(ns wckit.core-tests
  (:require 
    [clojure.test :refer :all]
    [wckit.core :as wckit]
    [wckit.core :refer [size]]
    ))

(def test-obj
  (->
    (wckit/create-new)
    (size 800 600)))

(deftest tests
  (testing "create-new"
    (wckit/create-new)))
    
