(ns wckit.helpers
  (:require
    [common.colors :as colors]
    [clojure.java.io :as io]
    )
  (:import
    (com.kennycason.kumo.palette ColorPalette)
    (java.awt Color)
    (wckit.core WCKitProto)))

; (defmacro build-color-pallette [cs]
  ; `(new ColorPalette
    ; (into-array
      ; java.awt.Color
      ; (map
        ; #(colors/create-color %)
        ; ~cs))))

(defn build-color-pallette [& cs]
  (new ColorPalette
    (into-array
      Color
      (map
        #(colors/create-color %)
        cs))))


(defn get-input-file [^WCKitProto wck]
  (let [
      filter-or-identity
        (if
          (get-in wck [:input-data :resource?])
          io/resource
          ;else
          identity)
    ]
    (io/file
      (filter-or-identity
        (get-in wck [:input-data :input-source])))))



