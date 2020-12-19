(ns advent-of-code-20.problem18.solution
  (:require [clojure.string :as str]))

(defn- matches [regex str]
  (not (nil? (re-matches regex str))))

(defn- to-int [string]
  (Integer/parseInt string))

(defn- apply-operator [a op b]
  (case op
    "*" (* a b)
    "-" (- a b)
    "+" (+ a b)))

(defn- parse-expression [expr] expr)

(defn- accumulate [{:keys [remaining acc] :as state}]
  (let [next (first remaining)]
    (case next
      nil (assoc state :done true)
      "(" (let [other-state (parse-expression (rest remaining))]
            (update (assoc state :remaining (:remaining other-state)) :acc conj (:acc other-state)))
      ")" (assoc (assoc state :done true) :remaining (rest remaining))
      (update (assoc state :remaining (rest remaining)) :acc conj (try (to-int next) (catch Exception e next))))))

(defn- parse-expression [terms]
  (->> (range)
       (reductions (fn [state _] (accumulate state)) {:done false :remaining terms :acc []})
       (filter #(get % :done))
       first))

(defn create-expression [expression]
  (:acc (parse-expression (vec (filter #(not= " " %) (str/split expression #""))))))

(defn- evaluate [terms]
  (if (= 1 (count terms))
    (if (number? (first terms)) terms (recur (first terms)))
    (let [lhs (first terms)
          op (second terms)
          rhs (nth terms 2)
          result (apply-operator (if (number? lhs) lhs (first (evaluate lhs))) op (if (number? rhs) rhs (first (evaluate rhs))))]
      (recur (concat [result] (rest (rest (rest terms))))))))

(defn evaluate-expression [expression]
  (let [parsed-expression (create-expression expression)]
    (first (evaluate parsed-expression))))

(defn part1 [input]
  (apply + (map evaluate-expression input)))

(defn part2 [input] 10)
