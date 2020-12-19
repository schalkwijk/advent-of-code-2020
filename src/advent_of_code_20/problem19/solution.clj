(ns advent-of-code-20.problem19.solution
  (:require [clojure.string :as str]))

(defn- to-int [string]
  (try (Integer/parseInt string) (catch Exception e string)))

(defn- create-rule [raw-rule]
  (let [[rule-number rule] (str/split (str/replace raw-rule #"\"" "") #": ")]
    [(to-int rule-number) (map #(map to-int (str/split % #" ")) (str/split rule #" \| "))]))

(defn- parse-rules [raw-rules]
  (into {} (map create-rule (str/split raw-rules #"\n"))))

(defn- is-valid? [sequence])

(defn parse-sequences [raw-sequences]
  (map #(str/split % #"") (str/split raw-sequences #"\n")))

(defn- is-valid-recur [rule-id {:keys [index sequence valid] :as state}]
  (let [rule (get-in state [:rules rule-id])
        maybe-leaf (first (first rule))]
    (if (not (number? maybe-leaf))
      (-> state
          (assoc :valid (and valid (= maybe-leaf (nth sequence index))))
          (update :index inc))
      (let [all-states (map #(reduce (fn [s rule-id] (is-valid-recur rule-id s)) state %) rule)
            valid-state (first (filter :valid all-states))]
        (if valid-state valid-state (first all-states))))))

(defn is-valid [rules sequence]
  (let [state {:valid true :sequence sequence :rules rules :index 0}
        final-state (is-valid-recur 0 state)]
    (and (= (:index final-state) (count sequence)) (:valid final-state))))

(defn part1 [input]
  (let [rules (parse-rules (first input))
        sequences (parse-sequences (last input))]
    (count (filter identity (map #(is-valid rules %) sequences)))))

(defn part2 [input] 10)
