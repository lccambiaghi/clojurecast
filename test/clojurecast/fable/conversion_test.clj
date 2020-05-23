(ns clojurecast.fable.conversion-test
  (:require [clojure.test :refer :all]
            [clojurecast.fable.conversion :refer :all]
            [clojisr.v1.r :refer [r r->clj clj->r]]
            [tech.v2.datatype.datetime :as dtype-dt]
            [tech.v2.datatype :as dtype]
            [tech.v2.datatype.datetime.operations :as dtype-dt-ops]
            [tech.ml.dataset :as ds]))

(def ds (ds/name-values-seq->dataset {"time" (dtype-dt-ops/plus-days (dtype-dt/instant) (range 5))
                                      "id"(repeat 5 "a")
                                      "value" (repeat 5 10)}))

(deftest convert-ds-dt-to-tsibble
  (let [robj (ds->tsibble mapseq-ds-dt {:index "time", :keys "id"})]
    (is (is-robj-tsibble? robj))
    (is (is-tsibble-col-date? robj "time"))))
