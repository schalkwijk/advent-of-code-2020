(ns advent-of-code-20.problem1.solution-test
  (:require [clojure.test :refer :all]
            [advent-of-code-20.problem7.solution :refer :all]
            [clojure.string :as str]))

(deftest part1-test
  (testing "baggify"
    (let [input "bright white bags contain 1 shiny gold bag."]
      (is (= ["bright white" [{:color "shiny gold" :count 1}]] (baggify input))))
    (let [input "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."]
      (is (= ["vibrant plum" [{:color "faded blue" :count 5} {:count 6 :color "dotted black"}]] (baggify input))))
    (let [input "dotted black bags contain no other bags."]
      (is (= ["dotted black" []] (baggify input)))))
  (testing "graphify"
    (let [input ["bright white bags contain 1 shiny gold bag." "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags." "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags, 1 dark olive bag."]]
      (is (= {"shiny gold" ["bright white"] "dark olive" ["vibrant plum" "shiny gold"] "vibrant plum" ["shiny gold"] "faded blue" ["vibrant plum"] "dotted black" ["vibrant plum"]} (graphify input)))))
  (testing "all together"
    (let [input (str/split (slurp "test/advent_of_code_20/problem7/input.txt") #"\n")]
      (is (= 4 (part1 input))))))

(deftest part2-test
  (testing "reverse-graphify"
    (let [input ["bright white bags contain 1 shiny gold bag." "shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags." "vibrant plum bags contain 5 faded blue bags, 6 dotted black bags."]]
      (is (= {"bright white" [{:count 1 :color "shiny gold"}]
              "shiny gold" [{:count 1 :color "dark olive"} {:count 2 :color "vibrant plum"}]
              "vibrant plum" [{:count 5 :color "faded blue"} {:count 6 :color "dotted black"}]}
             (reverse-graphify input)))))
  (testing "all together"
    (let [input (str/split (slurp "test/advent_of_code_20/problem7/input2.txt") #"\n")]
      (is (= 126 (part2 input))))))

