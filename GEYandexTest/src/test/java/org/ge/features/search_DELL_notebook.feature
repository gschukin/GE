Feature: As a user, I want to search for various notebooks manufactures

Scenario: I search Yandex Market for DELL notebook with price between 50k and 150k

Given I am on the main Yandex page
And I navigate to Yandex Market
And I select to "Компьютерная техника"
And I select to "Ноутбуки"
Then I see "Ноутбуки" page
And I set lower price to "50000"
And I set upper price to "150000"
And I set manufacture to "DELL"
And I set ascending price order
Then I should see only "DELL" notebooks with descending price