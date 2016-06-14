(require 'package)
(package-initialize)
;;add Themes Path
(add-to-list 'custom-theme-load-path "~/.emacs.d/themes/")

;;No more start screen buffer
(setq inhibit-startup-message t)

;;;;;;Multiple Cursors;;;;;
(global-set-key(kbd "C-c m c") 'mc/edit-lines)
(global-set-key (kbd "C->") 'mc/mark-next-like-this)
(global-set-key (kbd "C-<") 'mc/mark-previous-like-this)
(global-set-key (kbd "C-c C-<") 'mc/mark-all-like-this)
(global-set-key (kbd "C-c C-g") 'mc/unmark-previous-like-this)
(global-set-key (kbd "C-c C-n") 'mc/unmark-next-like-this)

;;iy-go-to-char config
(require 'iy-go-to-char)
(global-set-key (kbd "M-m") 'iy-go-to-char)
(global-set-key (kbd "M-,") 'iy-go-to-char-backward)
(global-set-key (kbd "M-n") 'iy-go-to-or-up-to-continue)
(global-set-key (kbd "M-p") 'iy-go-to-or-up-to-continue-backward)
(global-set-key (kbd "M-l") 'downcase-word)
(global-set-key (kbd "M-t") 'transpose-words)

;;Expand region
(require 'expand-region)
(global-set-key (kbd "C-ñ") 'er/expand-region)
(global-set-key (kbd "C-*") 'pending-delete-mode)
(global-set-key (kbd "C-?") 'er/contract-region)

;;Malabar mode
;(require 'cedet)
;(semantic-load-enable-minimum-features) ;; or enable more if you wish
;(require 'malabar-mode)
;(setq malabar-groovy-lib-dir "~/.emacs.d/elpa/malabar-mode-20140303.946")
;(add-to-list 'auto-mode-alist '("\\.java\\'" . malabar-mode))

;;Auto-complete Mode
(require 'auto-complete-config)
(add-to-list 'ac-dictionary-directories "~/.emacs.d/elpa/auto-complete-1.5.1/dict")
(ac-config-default)
(ac-linum-workaround)


;; customizing ac
(set-face-background 'ac-candidate-face "grey7")
(set-face-underline 'ac-candidate-face "darkgray")
(set-face-background 'ac-selection-face "dark slate gray")
(global-set-key (kbd "C-c a") 'auto-complete-mode)


;;CUSTOM CONFIGURATION
(custom-set-variables
 ;; custom-set-variables was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(ac-dictionary-directories (quote ("/home/abel/.emacs.d/elpa/auto-complete-1.5.1/dict")))
 '(ansi-color-names-vector ["#242424" "#e5786d" "#95e454" "#cae682" "#8ac6f2" "#333366" "#ccaa8f" "#f6f3e8"])
 '(cursor-type (quote box) t)
 '(custom-enabled-themes (quote (seti)))
 '(custom-safe-themes (quote ("a037894962137f98b0af7e66b9016414b43ca4c019eace48b3d27bd187db63e4" "135bbd2e531f067ed6a25287a47e490ea5ae40b7008211c70385022dbab3ab2a" "3c83b3676d796422704082049fc38b6966bcad960f896669dfc21a7a37a748fa" "a27c00821ccfd5a78b01e4f35dc056706dd9ede09a8b90c6955ae6a390eb1c1e" "acf633e5c121a8dc25cb1e5b5be2c818b0b62aff3fdb479a3c96e9a992da015b" "0d7ee39db806d2bae447fcc5a868f9b47eafae0a3464e2084a4a45462f2f4c2d" "937e1382f73751d40e63547bdf0c7498bdbbc1e0c04cbedc2500300dc02315d9" "72ac74b21322d3b51235f3b709c43c0721012e493ea844a358c7cd4d57857f1f" default)))
 '(exec-path (quote ("/usr/local/sbin" "/usr/local/bin" "/usr/sbin" "/usr/bin" "/sbin" "/bin" "/usr/games" "/usr/local/games" "/usr/lib/emacs/24.3/x86_64-linux-gnu" "~/apache-maven-3.3.9/bin" "~/Pygments-2.1.3" "~/jdk1.8.0_60/jre/bin" "~/jdk1.8.0_60/bin")))
 '(frame-background-mode (quote dark))
 '(global-auto-complete-mode nil)
 '(global-hl-line-mode t)
 '(helm-command-prefix-key "C-,")
 '(ido-enable-flex-matching t)
 '(ido-mode (quote both) nil (ido))
 '(jde-compiler (quote ("javac")))
 '(jde-jdk-registry (quote (("1.8.0_60" . "/home/abel/jdk1.8.0_60"))))
 '(jde-mode-line-format (quote ("-" mode-line-mule-info mode-line-modified mode-line-frame-identification mode-line-buffer-identification "   " global-mode-string "   %[(" mode-name mode-line-process minor-mode-alist "%n" ")%]--" (line-number-mode "L%l--") (column-number-mode "C%c--") (-3 . "%p") (jde-which-method-mode ("--" jde-which-method-format "--")))))
 '(menu-bar-mode t)
 '(org-export-backends (quote (ascii html icalendar latex odt)) t)
 '(org-export-latex-minted-options (quote (("breaklines" "true") ("breakautoindent" "true") ("breakanywhere" "false") ("linenos" "false") ("breaksymbolright" "\\small\\carriagereturn") ("escapeinside" "||") ("frame" "lines") ("fontsize" "\\footnotesize"))))
 '(org-src-preserve-indentation t)
 '(org-src-tab-acts-natively t)
 '(package-archives (quote (("gnu" . "http://elpa.gnu.org/packages/") ("melpa-stable" . "https://stable.melpa.org/packages/"))))
 '(server-mode nil)
 '(tool-bar-mode nil)
 '(uniquify-buffer-name-style (quote post-forward) nil (uniquify)))

(custom-set-faces
 ;; custom-set-faces was added by Custom.
 ;; If you edit it by hand, you could mess it up, so be careful.
 ;; Your init file should contain only one such instance.
 ;; If there is more than one, they won't work right.
 '(default ((t (:inherit nil :stipple nil :inverse-video nil :box nil :strike-through nil :overline nil :underline nil :slant normal :weight normal :height 128 :width normal :foundry "unknown" :family "Ubuntu Mono" :background "black"))))
 '(ac-candidate-face ((t (:inherit popup-face :background "grey7" :foreground "gray" :underline "darkgray"))))
 '(cursor ((t (:background "IndianRed4" :foreground "#151718"))))
 '(ido-first-match ((t (:box (:line-width 2 :color "dark slate gray" :style released-button) :weight extra-bold))))
 '(region ((t (:background "dim gray"))))
 '(show-paren-match ((t (:background "DodgerBlue4" :foreground "black"))))
 '(term-color-blue ((t (:background "midnight blue" :foreground "midnight blue"))) t))
(put 'downcase-region 'disabled nil)

(setq column-number-mode t)
;(global-linum-mode t)

;;smart mode line
;(setq sml/no-confirm-load-theme t)
;;Switching Buffer
;(global-set-key(kbd "s-SPC") 'next-buffer) ;THIS IS A TEST

;;ORG MODE
;;higlighting in pdf export
(require 'org-latex)
(setq org-export-latex-listings 'minted)
(add-to-list 'org-export-latex-packages-alist '("" "minted"))
(setq org-src-fontify-natively t) ;Can see higlighting in org mode file
;; code snippet comes from
;; http://joat-programmer.blogspot.com/2013/07/org-mode-version-8-and-pdf-export-with.html
;; Include the latex-exporter
;; check whether org-mode 8.x is available
;; (when (require 'ox-latex nil 'noerror)
;;   ;; Add minted to the defaults packages to include when exporting.
;;   (add-to-list 'org-latex-packages-alist '("" "minted"))
;;   ;; Tell the latex export to use the minted package for source
;;   ;; code coloration.
;;   (setq org-latex-listings 'minted)
;;   ;; Let the exporter use the -shell-escape option to let latex
;;   ;; execute external programs.
;;   ;; This obviously and can be dangerous to activate!
;;   (setq org-latex-pdf-process
;;         '("xelatex -shell-escape -interaction nonstopmode -output-directory %o %f")))


;Move to the last recent buffer
(global-set-key (kbd "s-SPC")  'mode-line-other-buffer)


(global-set-key (kbd "C-c r") 'recompile)
(global-set-key (kbd "C-c c") 'compile)

(add-to-list 'load-path "~/.emacs.d/elpa/cl-lib-0.5/")
(require 'cl-lib)
(require 'cl)
;;Powerline
(require 'powerline)
(powerline-default-theme)


;;Fixing justification in powerline mode
(powerline-raw mode-line-mule-info nil 'l)

;;Probando jdee
(add-to-list 'load-path "~/.emacs.d/jdee-2.4.1/lisp")
(load "jde")

;;Auto indent
(define-key global-map (kbd "RET") 'newline-and-indent)

(show-paren-mode t)
(sml/setup)

;ace-mode
(global-set-key (kbd "M-z") 'ace-window)

;;ace jump mode
(define-key global-map (kbd "C-x SPC") 'ace-jump-char-mode)
(define-key global-map (kbd "C-c SPC") 'ace-jump-mode)

;quit scrollbar
(scroll-bar-mode -1)

;;Helm
(require 'helm-config)
(global-set-key (kbd "M-x") 'helm-M-x)
(global-set-key (kbd "C-x b") 'helm-buffers-list)
(global-set-key (kbd "C-, .") 'helm-find)
(helm-mode 1)
(helm-autoresize-mode 1)


;; Comment/uncomment block
(global-set-key (kbd "C-c c") 'comment-or-uncomment-region)
(global-set-key (kbd "C-c u") 'uncomment-region)

;; Move windows, even in org-mode
(global-set-key (kbd "<s-right>") 'windmove-right)
(global-set-key (kbd "<s-left>") 'windmove-left)
(global-set-key (kbd "<s-up>") 'windmove-up)
(global-set-key (kbd "<s-down>") 'windmove-down)

;;Open line and indent
;(global-set-key (kbd "C-o") 'open-line-and-indent)

;TEST
(defun open-line-above ()
  (interactive)
  (beginning-of-line)
  (newline)
  (forward-line -1)
  (indent-for-tab-command))

(global-set-key (kbd "<C-s-return>") 'open-line-above)

(defun open-line-below ()
  (interactive)
  (end-of-line)
  (newline)
  (indent-for-tab-command))

(global-set-key (kbd "<C-return>") 'open-line-below)

;;end test

;;copy line
(defun xah-copy-line-or-region ()
  "Copy current line, or text selection.
When called repeatedly, append copy subsequent lines.
When `universal-argument' is called first, copy whole buffer (respects `narrow-to-region').

URL `http://ergoemacs.org/emacs/emacs_copy_cut_current_line.html'
Version 2015-12-30"
  (interactive)
  (let (ξp1 ξp2)
    (if current-prefix-arg
        (setq ξp1 (point-min) ξp2 (point-max))
      (if (use-region-p)
          (setq ξp1 (region-beginning) ξp2 (region-end))
        (setq ξp1 (line-beginning-position) ξp2 (line-end-position))))
    (if (eq last-command this-command)
        (progn
          ;; (push-mark (point) "NOMSG" "ACTIVATE")
          (kill-append "\n" nil)
          (forward-line 1)
          (end-of-line)
          (kill-append (buffer-substring-no-properties (line-beginning-position) (line-end-position)) nil)
          (message "Line copy appended"))
      (progn
        (kill-ring-save ξp1 ξp2)
        (if current-prefix-arg
            (message "Buffer text copied")
          (message "Text copied"))))))

(global-set-key (kbd "<f7>") 'xah-copy-line-or-region) ; copy


;yasnippet
(add-to-list 'load-path
	      "~/.emacs.d/elpa/yasnippet")
(require 'yasnippet)
(yas-global-mode 1)
