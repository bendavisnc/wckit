(ns wckit.wc-builder
  (:require
    [common.colors :as colors])
  (:import
    (com.kennycason.kumo.nlp FrequencyAnalyzer)
    (com.kennycason.kumo WordCloud)
    (com.kennycason.kumo CollisionMode)
    (com.kennycason.kumo.font.scale LinearFontScalar)
    (java.awt Dimension)
    )
  )

    ; (java.awt Color)
(defn build [wck]
  (let [
      dimension (apply #(new java.awt.Dimension %1 %2) (:size wck))
      frequency-analyzer 
        (doto
          (new FrequencyAnalyzer)
          (.setWordFrequenciesToReturn (:limit wck)))
      word-frequencies (.load frequency-analyzer (get-in wck [:input-data :input-source]))
      kumo-wc (new WordCloud dimension, CollisionMode/RECTANGLE)
      font-size (:font-size wck)
    ]
    (doto
      kumo-wc
      (.setBackgroundColor 
        (colors/create-color (:background-color wck)))
      (.setFontScalar (new LinearFontScalar font-size font-size))
      (.build word-frequencies))))





