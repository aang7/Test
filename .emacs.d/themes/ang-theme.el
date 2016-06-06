(deftheme ang
  "Created 2016-03-16.")

(custom-theme-set-variables
 'ang
 '(ansi-color-names-vector ["#242424" "#e5786d" "#95e454" "#cae682" "#8ac6f2" "#333366" "#ccaa8f" "#f6f3e8"])
 '(cursor-type (quote box))
 '(custom-safe-themes (quote ("937e1382f73751d40e63547bdf0c7498bdbbc1e0c04cbedc2500300dc02315d9" "72ac74b21322d3b51235f3b709c43c0721012e493ea844a358c7cd4d57857f1f" default)))
 '(ido-enable-flex-matching t)
 '(package-archives (quote (("gnu" . "http://elpa.gnu.org/packages/") ("melpa-stable" . "https://stable.melpa.org/packages/"))))
 '(uniquify-buffer-name-style (quote post-forward))
 '(tool-bar-mode nil)
 '(server-mode t)
 '(menu-bar-mode t)
 '(ido-mode (quote both)))

(custom-theme-set-faces
 'ang
 '(default ((t (:inherit nil :stipple nil :foreground "#e1e1e0" :background "black" :inverse-video nil :box nil :strike-through nil :overline nil :underline nil :slant normal :weight normal :height 128 :width normal :foundry "unknown" :family "Ubuntu Mono"))))
 '(cursor ((t (:background "IndianRed4" :foreground "#151718"))))
 '(ido-first-match ((t (:box (:line-width 2 :color "dark slate gray" :style released-button) :weight extra-bold)))))

(provide-theme 'ang)
