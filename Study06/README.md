# 第6回 タブーサーチとグラフ理論

### 概要
交通や発電・送電システムなどの社会インフラの安全性は重要な課題である．
これらは個々の要素(駅・空港や発電所)の破壊に対して，
全体の機能が影響されなければ強いシステムと言うことができる．

システムを強くするナイーブな解決策は，すべての要素同士を直接接続することであるが，
この解決策ではコストがかさむことが問題である(線路，送電線など)．

そこで，要素ごとのつながりをつなぎ変えることによって，コストの問題を解決する
(空港の場合，行き先を変更するだけでよい)．

そこで今回は，まず，これらのシステムを数学的に表現する手法である**グラフ**を簡単に説明し，
タブーサーチを用いて，辺のつなぎ替えによるネットワーク強化問題を解く．

### 参考文献
Sun, Shi-wen, et al. "Tabu Search enhances network robustness under targeted attacks." Physica A: Statistical Mechanics and its Applications 446 (2016): 82-91.

Mat Buckland　著、松田 晃一　訳 実例で学ぶゲームAIプログラミング オライリー・ジャパン 2007年09月