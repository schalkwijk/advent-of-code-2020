(ns advent-of-code-20.problem2.solution
  (:require [clojure.math.combinatorics :refer :all]
            [clojure.string :as str]))

(defn to-int [string]
  (Integer/parseInt string))

(defn- split-raw-password [password]
  (let [[min max char pass] (str/split (str/replace password #":" "") #"[ -]")]
    {:lower (to-int min) :upper (to-int max) :char char :password (str/split pass #"")}))

(defn- valid-count-password? [raw-password]
  (let [password (split-raw-password raw-password)
        char-count (count (filter #(= % (:char password)) (:password password)))]
    (and (>= char-count (:lower password)) (<= char-count (:upper password)))))

(defn- valid-occurrence-password? [raw-password]
  (let [password (split-raw-password raw-password)
        first-match (= (:char password)(nth (:password password) (dec (:lower password))))
        second-match (= (:char password)(nth (:password password) (dec (:upper password))))]
    (and (or first-match second-match) (not (= first-match second-match)))))

(defn part1 [passwords]
  (count (filter valid-count-password? passwords)))

(defn part2 [passwords]
  (count (filter valid-occurrence-password? passwords)))
