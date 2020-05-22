(ns clojurecast.fable.conversion-test
  (:require [clojure.test :refer :all]
            [tech.ml.dataset :as ds]
            [clojurecast.fable.conversion :refer [ds->tsibble]])
  (:import clojisr.v1.robject.RObject)
  )

(deftest ds-is-converted
  (let [ds (ds/name-values-seq->dataset {:time [1 2 3 4 5]
                                         :id ["a" "a" "a" "a" "a"]
                                         :value [10 10 10 10 10]})
        tsibble (ds->tsibble ds {:index :time, :key :id})]
    (is (= clojisr.v1.robject.RObject (class tsibble)))))
