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

(defn- is-valid-recur [rule-id {:keys [index sequence valid required-characters] :as state}]
  (if (or (>= required-characters (count sequence)) (not valid))
    [(assoc state :valid false)]
    (let [rule (get-in state [:rules rule-id])
         maybe-leaf (first (first rule))]
     (if (not (number? maybe-leaf))
       [(-> state
            (assoc :valid (and valid (= maybe-leaf (nth sequence index))))
            (update :index inc))]
       (mapcat (fn [subrule] (reduce-kv (fn [states index rule-id] (mapcat (fn [s] (is-valid-recur rule-id (update s :required-characters + (if (zero? index) 0 1)))) states)) [state] (vec subrule))) rule)))))

(defn is-valid [rules sequence]
  (let [state {:valid true :sequence sequence :rules rules :index 0 :required-characters 0}
        final-states (is-valid-recur 0 state)]
    (some #(and (:valid %) (= (count sequence) (:index %))) final-states)))

(defn part1 [input]
  (let [rules (parse-rules (first input))
        sequences (parse-sequences (last input))]
    (count (filter identity (map #(is-valid rules %) sequences)))))

(defn- update-rules [rules]
  (-> rules
      (assoc 8 [[42] [42 8]])
      (assoc 11 [[42 31] [42 11 31]])))

(defn part2 [input]
  (let [rules (update-rules (parse-rules (first input)))
        sequences (parse-sequences (last input))]
    (count (filter identity (map #(is-valid rules %) sequences)))))
