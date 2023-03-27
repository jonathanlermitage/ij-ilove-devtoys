# I Love DevToys Change Log

## 1.9.0 (WIP)
* add Password verifier tool. Thx to contributor **kpouer**.
* dependencies upgrade.

## 1.8.0 (2023/03/15)
* add BCrypt (2A, 2B, 2Y) to Hash tool. Thx to contributor **kpouer**.
* dependencies upgrade.

## 1.7.1 (2023/02/18)
* fix Data Faker tool: remove scientific names attached to animal names since there was to link between them (both were random).
* Data Faker tool now tries to produce unique and sorted items.
* dependencies upgrade.

## 1.7.0 (2023/01/21)
* add HMAC tool.
* add Lines merging tool: add the lines of B to A if they're new.
* add Lines subtract tool: remove the lines of B from A.

## 1.6.0 (2023/01/07)
* fix some bugs with Timestamp tool. 
* Timestamp tool now supports epoch in milliseconds.
* add IBAN to Data Faker tool.

## 1.5.0 (2022/12/17)
* add [zxcvbn](https://github.com/nulab/zxcvbn4j) Password strength evaluator.
* add more locales to the Data Faker tools.
* dependencies upgrade: fix Data Faker tools for some non-English locales.

## 1.4.0 (2022/11/17)
* improve Cron parser tool.
* add JSON support to (un)Escaper tool.
* minor code rework.

## 1.3.0 (2022/11/11)
* add ASCII <> HEX tool. Thx to contributor **kpouer**.
* add (un)Escaper tool.
* add Cron parser tool.
* add JSON to String tool.
* add Properties to YAML tool (including Spring profiles support).
* rework tools ordering.
* hopefully fix TextAreas unwanted resizing.
* various UI improvements.
* dependencies upgrade.

## 1.2.0 (2022/11/01)
* add BENCODE <> JSON tool. Thx to contributor **kpouer**.
* add Timestamp tool.
* add Data Faker tool.
* add Set Diff tool: it tells if two files have the same lines but in any order.

## 1.1.0 (2022/10/23)
* reworked icon renderer and improved quality of tool icons.

## 1.0.0 (2022/10/21)
* first stable release 🎉.
* add Base64 tool.
* add Hash (md5, sha1, sha256, sha384, sha512) tool.
* add JSON <> YAML tool.
* add Lorem Ipsum tool.
* add URL tool.
* add UUID tool.
