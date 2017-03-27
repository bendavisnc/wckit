(ns wckit.filter.filtertests
  (:require
    [wckit.core]
    [clojure.test :refer :all])
  )
  ;(:import
  ;  (wckit.java.filter TopEnglishWordsFilter))
  ;  )

;(:gen-class)
;[wckit.core]

;(def test-filter
  ;(new TopEnglishWordsFilter))

(deftest tests
  (testing "top english word filter"
    (assert
      (= 1 2))))
      ;(.apply
      ;  test-filter
      ;  "cheese"))))

