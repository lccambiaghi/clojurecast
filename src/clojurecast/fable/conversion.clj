(ns clojurecast.fable.conversion
  (:require [clojisr.v1.r :refer [r r->clj]]
            [clojisr.v1.require :refer [require-r]]
            [tech.v2.datatype.datetime :as dtype-dt]
            [tech.v2.datatype :as dtype]
            [tech.v2.datatype.datetime.operations :as dtype-dt-ops]
            [tech.ml.dataset :as ds]))

(require-r '[tsibble]
           '[lubridate]
           '[dplyr])

(defn- is-packed-instant? [col]
  (= :packed-instant (dtype/get-datatype col)))

(defn- instant->seconds [ds col]
  (if (is-packed-instant? (ds col))
    (ds/update-column ds col #(dtype-dt-ops/get-epoch-days %))
    ds))

(defn is-robj-tsibble? [robj]
  (= (-> (r.base/class robj)
         (r->clj)
         (first))
     "tbl_ts"))

(defn is-tsibble-col-date? [tsibble col]
  (= (-> (r.base/$ tsibble col)
         (r.base/class)
         (r->clj)
         (first))
     "Date"))

(defn ds->tsibble
  "
  `:ds` must be a dataset
  `:index` is a column of instant type a string denoting the time column
  `:keys` is a string or a list of strings to identify the unique time series
   "
  [ds {:keys [index keys]}]
  (-> (instant->seconds ds index)
      (r.tsibble/as-tsibble :index index :key keys)
      (r.dplyr/mutate (keyword index) `(r.lubridate/as_date ~(symbol index)))))
