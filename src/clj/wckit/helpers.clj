(ns wckit.helpers
  (:require
    [common.colors :as colors])
  (:import
    (com.kennycason.kumo.palette ColorPalette)
    (java.awt Color)
    ))

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

