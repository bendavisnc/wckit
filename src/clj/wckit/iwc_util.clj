(ns wckit.iwc-util
  (:require
    [common.colors :as colors]
    [clojure.java.io :as io]
    [clojure.edn :as edn])
  (:import
    [com.kennycason.kumo CollisionMode]
    [com.kennycason.kumo PolarBlendMode]
    [com.kennycason.kumo.font FontWeight]
    [com.kennycason.kumo.cli CliParameters$FontScalarType]
    [com.kennycason.kumo.cli CliParameters$TokenizerType]
    [com.kennycason.kumo.cli CliParameters$WordStartType]
    [com.kennycason.kumo.cli CliParameters$Type]
    [com.kennycason.kumo.cli CliParameters$NormalizerType]
    [wckit.java.builder WCBuilder]
    [wckit.java.filter FilterType]
    )
)

(defn get-background-color [wck]
  (colors/create-color
    (:background-color wck)))

(defn get-layered-colors-edn [d]
  (mapv
    (fn [v]
      (mapv
        #(colors/create-color %)
        v))
    d))
;;
;; A fallback for when the wrapped cli parser fails for whatever reason.
(defn get-colors-edn [d]
  (map
    #(colors/create-color %)
    d))

(defn get-colors [wck]
  (let [
      ; whatever data's provided
      raw-color-input (:color wck)
      ; see if the input is a string that maybe would be parsed using cli.
      use-cli-parser? (string? raw-color-input)
      ; just make sure it's a collection
      seq-color-input (or (and (not (sequential? raw-color-input)) [raw-color-input]) raw-color-input)
      cli-parser-results
        (and
          use-cli-parser?
          (try
            (WCBuilder/getColors raw-color-input)
            (catch Exception e
              nil)))
    ]
    (if-let [successful-results cli-parser-results]
      successful-results
      (get-colors-edn seq-color-input))))
      ;(throw (new Exception "Problem parsing font colors.")))))

(defn get-layered-colors [wck]
  (let [
      raw-color-input (:color wck)
      use-cli-parser? (string? raw-color-input)
      seq-color-input (or (and (not (sequential? raw-color-input)) [raw-color-input]) raw-color-input)
      cli-parser-results
        (and
          use-cli-parser?
          (try
            (WCBuilder/getLayeredColors raw-color-input)
            (catch Exception e
              nil)))
    ]
    (if-let [successful-results cli-parser-results]
      successful-results
      (get-layered-colors-edn seq-color-input))))
      ;(throw (new Exception "Problem parsing font colors.")))))

(defn get-collision-mode [wck]
  (let [
      v (keyword (:collision wck))
      good-v (v {:pixel-perfect CollisionMode/PIXEL_PERFECT, :rectangle CollisionMode/RECTANGLE})
    ]
    (or
      good-v
      (throw (new Exception "Invalid collision mode.")))))

(defn get-polar-blend-mode [wck]
  (let [
        v (keyword (:polar-blend-mode wck))
        good-v (v {:even PolarBlendMode/EVEN, :blur PolarBlendMode/BLUR})
        ]
    (or
      good-v
      (throw (new Exception "Invalid polar blend mode.")))))

(defn get-font-scalar-type [wck]
  (let [
        v (keyword (:font-scalar wck))
        good-v (v {:linear CliParameters$FontScalarType/LINEAR, :sqrt CliParameters$FontScalarType/SQRT,  :log CliParameters$FontScalarType/LOG})
        ]
    (or
      good-v
      (throw (new Exception "Invalid font scalar type.")))))

(defn get-font-weight [wck]
  (let [
        v (keyword (:font-weight wck))
        good-v (v {:bold FontWeight/BOLD, :italic FontWeight/ITALIC :plain FontWeight/PLAIN})
        ]
    (or
      good-v
      (throw (new Exception "Invalid font weight.")))))

(defn get-normalizers [wck]
  ;(into-array com.kennycason.kumo.cli.CliParameters.NormalizerType
  (new java.util.ArrayList []))

(defn get-output-source [wck]
  (or
    (:output wck)
    (str
      (->
        (:config-origin wck)
        (io/file)
        (.getParentFile)
        (.getCanonicalPath)
        )
      "/render.png")))

(defn get-tokenizer [wck]
  (let [
        v (keyword (:tokenizer wck))
        good-v (v {:chinese CliParameters$TokenizerType/CHINESE, :english CliParameters$TokenizerType/ENGLISH, :whitespace CliParameters$TokenizerType/WHITE_SPACE})
        ]
    (or
      good-v
      (throw (new Exception "Invalid tokenizer.")))))

(defn get-type [wck]
  (let [
        v (keyword (:wc-type wck))
        good-v (v {:layered CliParameters$Type/LAYERED, :polar CliParameters$Type/POLAR, :standard CliParameters$Type/STANDARD})
        ]
    (or
      good-v
      (throw (new Exception "Invalid word cloud type.")))))


(defn get-word-start-type [wck]
  (let [
        v (keyword (:word-start wck))
        good-v (v {:center CliParameters$WordStartType/CENTER, :random CliParameters$WordStartType/RANDOM})
        ]
    (or
      good-v
      (throw (new Exception "Invalid word start type.")))))


(defn get-filters [wck]
  (map
    (fn [s]
      (or
        (
          {
            "top-english" FilterType/TOP_ENGLISH
          }
          s)
        (throw (new Exception "Invalid filter type."))))
    (:filters wck)))


