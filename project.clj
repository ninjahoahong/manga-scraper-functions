(defproject manga-scraper "0.1.0-SNAPSHOT"
  :main manga-scraper.core
  :description "A collection of clojure helper functions to help scrape manga from different manga websites"
  :license
  {:name "MIT"
   :url  "MIT"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [enlive "1.1.6"]
                 [compojure "1.6.1"]
                 [http-kit "2.3.0"]])
