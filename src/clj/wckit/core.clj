(ns wckit.core
  (:import
    (com.kennycason.kumo.nlp FrequencyAnalyzer)
    (com.kennycason.kumo WordCloud)
    (com.kennycason.kumo CollisionMode)
    (com.kennycason.kumo.font.scale LinearFontScalar)
    (java.awt Dimension)
    (java.awt Color))
  (:require
    [wckit.wc-input :as wc-input]
    [wckit.wc-builder :as wc-builder]
    [clojure.java.io :as io]
    [common.helpers :refer [init-apache-logging!]]
    )
  )

(init-apache-logging!) ; I said shut up, you!

(defprotocol WCKitProtocol
  "Interface for core wckit usage."

  (size [this, ^Integer width, ^Integer height]
    "The size (x and y resolution) of the word cloud render.")

  (limit [this, ^Integer n]
    "The maximum amount of individual words to render.")

  (font-color [this, cs]
    "A list (single value allowed) representing  a scale of color.")

  (font-size [this, ^Integer v]
    "Data representing the minimum and maximum font size scale.")

  (font-style [this, ^String s]
    "Data representing font style.")

  (min-font-size [this, ^Integer v]
    "Set the minimum for font size scale.")

  (max-font-size [this, ^Integer v]
    "Set the maximum for font size scale.")

  (collision-mode [this, ^CollisionMode m]
    "Which collision mode algorithm to use.")

  (background-color [this, ^String c]
    "The plain background color to use for the render.")

  (input [this, args]
    "Data representing input data.")

  (spit-png [this, ^String filepath]
    "The method that actually does the thing. Spits out a wordcloud png according to what the filepath says.")
  )


(defrecord WCKitProto [
    input-data,
    limit,
    size,
    font-data,
    ^CollisionMode collision-mode
    ;collision-mode,
    background-color
  ]
  WCKitProtocol
    (size [this, width, height]
      (assoc this :size [width, height]))
    (limit [this, v]
      (assoc this :limit v))
    (font-color [this, c]
      (assoc-in this [:font-data :color] c))
    (font-style [this, s]
      (assoc-in this [:font-data :style] s))
    (font-size [this, v] ; todo - cleanup
      (assoc-in  
        (assoc-in this [:font-data :max-size] v)
          [:font-data :min-size] v))
    (max-font-size [this, v]
      (assoc-in this [:font-data :max-size] v))
    (min-font-size [this, v]
      (assoc-in this [:font-data :min-size] v))
    ;(collision-mode [this, ^CollisionMode m]
    (collision-mode [this, m]
      (assoc this :collision-mode m))
    (background-color [this, c]
      (assoc this :background-color c))
    (input [this, args]
      (wc-input/input this args))
    (spit-png [this, filepath]
      (.writeToFile
        (wc-builder/build this) filepath))
    )

(def ^:private defaults
  {
    :input-data {}
    :size [800, 600]
    :limit 100
    :font-data {
        :min-size 10
        :max-size 50
        :color "white"
        :style "impact"
      }
    :collision-mode CollisionMode/PIXEL_PERFECT
    :background-color "black"
  })
 

(defn create-new []
  (map->WCKitProto defaults))

