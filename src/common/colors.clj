(ns common.colors
  (:import
    (javafx.scene.paint Color))
  )


(defn create-color [v]
  (let [
      fxcolor (Color/web v)
    ]
    (new java.awt.Color
      (.getRed fxcolor)
      (.getGreen fxcolor)
      (.getBlue fxcolor)
      (.getOpacity fxcolor))))
