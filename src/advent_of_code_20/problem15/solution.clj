(ns advent-of-code-20.problem15.solution
  (:require [clojure.string :as str]))

(defn- to-int [string]
  (Integer/parseInt string))

(defn- create-ledger [numbers]
  (into {} (map vector numbers (range))))

(defn compute-next-number [{:keys [target-number ledger cnt] :as state}]
  (let [index (get ledger target-number)
        new-ledger (assoc ledger target-number cnt)
        new-number (if (= index nil) 0 (- cnt index))]
    (update (assoc (assoc state :target-number new-number) :ledger new-ledger) :cnt inc)))

(defn part1
  ([input] (part1 input 2020))
  ([input c](let [starting-numbers (vec (map to-int (str/split (first input) #",")))
                  state {:target-number 0 :ledger (create-ledger starting-numbers) :cnt (count starting-numbers)}]
              (->> starting-numbers
                   count
                   inc
                   (- c)
                   (nth (reductions (fn [state index] (compute-next-number state)) state (range)))
                   :target-number))))

(defn part2 [input] (part1 input 30000000))
