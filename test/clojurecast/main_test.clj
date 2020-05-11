(ns clojurecast.main-test
  (:require [clojure.test :refer :all]
            [clojurecast.main :refer :all]))


(deftest adding-numbers
  (is (= 4 (+ 2 2))))

(deftest dividing-numbers
  (is (= 2 (/ 4 2))))

(deftest dividing-numbers-by-zero
  (is (thrown? ArithmeticException (/ 1 0))))
