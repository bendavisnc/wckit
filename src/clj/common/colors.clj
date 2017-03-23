(ns common.colors
  (:import
    (javafx.scene.paint Color))
  )

;;
;;
;; I don't won't this class to exist.
;; If there's a better class to use, please send me a pull request.


(defn create-color [^String v]
  "Given a string v, parse it into a java.awt.Color."
  (let [
      fxcolor (Color/web v)
    ]
    (new java.awt.Color
      (.getRed fxcolor)
      (.getGreen fxcolor)
      (.getBlue fxcolor)
      (.getOpacity fxcolor))))
