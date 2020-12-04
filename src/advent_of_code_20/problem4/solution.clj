(ns advent-of-code-20.problem4.solution
  (:require [clojure.string :as str]))

(defn- to-int [string]
  (Integer/parseInt string))

(defn- valid-byr? [byr]
  (let [year (to-int byr)]
    (and (= 4 (count byr)) (>= year 1920 ) (<= year 2002))))

(defn- valid-iyr? [iyr]
  (let [year (to-int iyr)]
    (and (= 4 (count iyr)) (>= year 2010) (<= year 2020))))

(defn- valid-eyr? [eyr]
  (let [year (to-int eyr)]
    (and (= 4 (count eyr)) (>= year 2020) (<= year 2030))))

(defn- valid-hgt? [hgt]
  (let [[raw-value metric] (rest (re-find (re-matcher #"(.*)(cm|in)" hgt)))
        value (to-int (or raw-value "0"))]
    (case metric
      "cm" (and (>= value 150) (<= value 193))
      (and (>= value 59) (<= value 76)))))

(defn- valid-hcl? [hcl]
  (.matches (re-matcher #"#[0-9a-f]{6}" hcl)))

(defn- valid-ecl? [ecl]
  (contains? (set ["amb" "blu" "brn" "gry" "grn" "hzl" "oth"]) ecl))

(defn- valid-pid? [pid]
  (= 9 (count pid)))

(defn- generate-passport [raw-passport]
  (let [attributes (map #(str/split % #":") (str/split raw-passport #" "))]
    (into {} (map (fn [[k v]] [(keyword k) v]) attributes))))

(def required-fields
  [:byr :iyr :eyr :hgt :hcl :ecl :pid])

(defn- valid-passport? [passport]
  (every? (partial contains? passport) required-fields))

(def fields-to-validator
  (map vector required-fields [valid-byr? valid-iyr? valid-eyr? valid-hgt? valid-hcl? valid-ecl? valid-pid?]))

(defn- valid-field? [passport [field validator]]
  (and (not (nil? (field passport))) (validator (field passport))))

(defn- validest-passport? [passport]
  (every? (partial valid-field? passport) fields-to-validator))

(defn part1 [passports]
  (count (filter valid-passport? (map generate-passport passports))))

(defn part2 [passports]
  (count (filter validest-passport? (map generate-passport passports))))
