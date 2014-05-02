define("common/wx/RichBuddy.js", [ "common/qq/emoji.js", "tpl/RichBuddy/RichBuddyLayout.html.js", "tpl/RichBuddy/RichBuddyContent.html.js", "xss/ui/rich_buddy.css", "common/wx/Tips.js", "common/qq/Class.js", "common/wx/remark.js", "user/user_cgi.js", "common/qq/events.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict", e("common/qq/emoji.js");
        var i = e("tpl/RichBuddy/RichBuddyLayout.html.js"), s = e("tpl/RichBuddy/RichBuddyContent.html.js"), o = template.compile(s), u = e("xss/ui/rich_buddy.css"), a = e("common/wx/Tips.js"), f = e("common/qq/Class.js"), l = e("common/wx/remark.js"), c = e("common/qq/emoji.js"), h = e("user/user_cgi.js"), p = e("common/qq/events.js"), d = p(!0), v = {}, m = {
            position: {
                left: 0,
                top: 0
            },
            fakeId: ""
        }, g = f.declare({
            init: function () {
            },
            $element: null,
            $content: null,
            hideTimer: null,
            namespace: ".RichBuddy",
            options: {},
            _init: function () {
                var e = this.options, t = this, n, r = e.fakeId, s = e.position;
                this._unbindEvent(), this.$element || (this.$element = $(i).appendTo(document.body)), this.$content = this.$element.find(".buddyRichContent"), this.$loading = this.$element.find(".loadingArea"), v[r] ? (n = v[r], this.$content.html(o(n)), this._hideLoading(), this._bindEvent()) : (this._showLoading(), h.getBuddyInfo(r, function (n) {
                    if (!n || !n.base_resp) {
                        a.err("系统出错，请稍后重试！");
                        return;
                    }
                    if (n.base_resp.ret == 0) {
                        var i = n.contact_info;
                        i.groups = n.groups.groups, n = i;
                        for (var s in n) typeof n[s] == "string" && (n[s] = n[s].replace(/&nbsp;/ig, " "));
                        n.nick_name = n.nick_name.emoji(), n.fake_id == e.fakeId && (t._hideLoading(), t.$content.html(o(n)), t._bindEvent()), n.fake_id && (v[r] = n);
                    } else switch (+n.base_resp.ret) {
                        case 1:
                            a.err("该用户已经对您取消关注");
                            break;
                        case 2:
                            break;
                        case -3:
                            a.err("会话过期，请重新登录");
                            break;
                        default:
                            a.err("系统出错，请稍后重试！");
                    }
                }));
            },
            _showLoading: function () {
                this.$loading.show(), this.$content.hide();
            },
            _hideLoading: function () {
                this.$loading.hide(), this.$content.show();
            },
            _bindEvent: function () {
                var e = this, t = this.options;
                this.$element.bind("mouseover" + this.namespace, function (t) {
                    clearTimeout(e.hideTimer), e.hideTimer = null, e.$element.show();
                }).bind("mouseout" + this.namespace, function (t) {
                    e._mouseout();
                }), this.$element.find(".js_changeRemark").bind("click" + this.namespace, function () {
                    var e = v[t.fakeId];
                    d.trigger("Remark:change", t.fakeId, e.remark_name);
                }), d.on("Remark:changed", function (t, n) {
                    v[t] && (v[t].remark_name = n), e.$element.find(".js_remarkName").html(n);
                }), this.$element.find(".group").bind("change" + this.namespace, function () {
                    var e = $(this).val(), n = t.fakeId;
                    h.changeGroup(n, e, function (t) {
                        v[n].GroupID = e;
                    });
                });
            },
            _unbindEvent: function () {
                if (this.$element) {
                    var e = this.namespace;
                    this.$element.find(".js_changeRemark").unbind(e), this.$element.unbind(e), this.$element.find(".group").unbind(e), this.$element.stop(), this.$element.css("opacity", 1), this.$element.show();
                }
            },
            _mouseout: function () {
                var e = this;
                e.hideTimer || (e.hideTimer = setTimeout(function () {
                    !!e.$element && e.$element.fadeOut(), e.hideTimer = null;
                }, 1e3));
            },
            show: function (e) {
                var t = this.options.fakeId;
                e && (this.options = e), clearTimeout(this.hideTimer), this.hideTimer = null, e.fakeId !== t && this._init(), this.$element.css(e.position), this.$element.show();
            },
            hide: function () {
                this._mouseout();
            }
        });
        n.exports = g;
    } catch (y) {
        wx.jslog({
            src: "common/wx/RichBuddy.js"
        }, y);
    }
});
define("tpl/msgListItem.html.js", [], function (e, t, n) {
    return '{if list.length <= 0}\n    <p class="empty_tips">暂无消息</p>\n{else}\n    {each list as item as index}\n    <li class="message_item {if item.has_reply}replyed{/if}" id="msgListItem{item.id}" data-id="{item.id}">\n        {if (item.fakeid != uin)}\n        <div class="message_opr">\n            <a href="javascript:;" data-id="{item.id}" data-tofakeid="{item.fakeid}" class="icon20_common reply_gray js_reply" title="快捷回复">快捷回复</a>\n            {if (item.type != 10 || item.app_sub_type != 2) }\n            <a href="javascript:;" class="js_star icon16_common {if (item.is_starred_msg != 1)}star_gray{else}star_orange{/if}" action="{action}" idx="{item.id}" starred="{item.is_starred_msg}" title="{if (item.is_starred_msg != 1) }收藏消息{else}取消收藏{/if}">取消收藏</a>\n            {/if}\n            {if (item.type!= 1 && item.type != 10 && item.type != 4) }\n            <a href="javascript:;" class="js_save icon16_common save_gray" idx="{item.id}" data-type="{item.type}" title="保存为素材">保存为素材</a>\n            <a href="/cgi-bin/downloadfile?msgid={item.id}&source={item.source}&token={token}" class="icon18_common download_gray" target="_blank" idx="{item.id}" title="下载">下载</a>\n            {/if}\n            {if (item.type == 4) }\n            <a href="/cgi-bin/downloadfile?msgid={item.id}&source={item.source}&token={token}" class="icon18_common download_gray" target="_blank" idx="{item.id}" title="下载">下载</a>\n            {/if}\n        </div>\n        {/if}\n        <div class="message_info">\n            <div class="message_status"><em class="tips"><i>●</i>已回复</em></div>\n            <div class="message_time">{timeFormat item}</div>\n            <div class="user_info">\n                {if (item.fakeid != uin)}\n                <a href="{id2singleURL item}" target="_blank" data-fakeid="{item.fakeid}" class="remark_name">{if item.remark_name}{=item.remark_name}{else}{=item.nick_name.emoji()}{/if}</a>\n                {else}\n                <span data-fakeid="{item.fakeid}" class="remark_name">{if item.remark_name}{=item.remark_name}{else}{=item.nick_name.emoji()}{/if}</span>\n                {/if}\n                \n                <span class="nickname" data-fakeid="{item.fakeid}">{if item.remark_name}(<strong>{=item.nick_name.emoji()}</strong>){/if}</span>\n                \n                {if (item.fakeid != uin)}\n                <a href="javascript:;" class="icon14_common edit_gray js_changeRemark" data-fakeid="{item.fakeid}" title="修改备注"></a>\n                {/if}\n                {if (item.fakeid != uin)}\n                <a target="_blank" href="{id2singleURL item}" class="avatar" data-fakeid="{item.fakeid}">\n                    <img src="https://res.wx.qq.com/mpres/htmledition/images/icon/page-setting/avatar/icon80_avatar_default.png" data-src="/misc/getheadimg?token={token}&fakeid={item.fakeid}" data-fakeid="{item.fakeid}">\n                </a>\n                {else}\n                <span class="avatar" data-fakeid="{item.fakeid}">\n                    <img src="https://res.wx.qq.com/mpres/htmledition/images/icon/page-setting/avatar/icon80_avatar_default.png" data-src="/misc/getheadimg?token={token}&fakeid={item.fakeid}" data-fakeid="{item.fakeid}">\n                </span>\n                {/if}\n            </div>\n        </div>\n\n        <div class="message_content {if item.type == 1}text{/if}">\n            <div id="wxMsg{item.id}" data-id="{item.id}" class="wxMsg">\n                {mediaInit item}\n            </div>\n        </div>\n\n        {if (item.fakeid != uin)}\n        <div id="quickReplyBox{item.id}" class="js_quick_reply_box quick_reply_box">\n            <label for="" class="frm_label">快速回复:</label>\n            <div class="emoion_editor_wrp js_editor"></div>\n            <div class="verifyCode"></div>\n            <p class="quick_reply_box_tool_bar">\n                <span class="btn btn_primary btn_input" data-id="{item.id}">\n                    <button class="js_reply_OK" data-id="{item.id}" data-fakeid="{item.fakeid}">发送</button>\n                </span>\n                <a class="js_reply_pickup pickup" data-id="{item.id}" href="javascript:;">收起</a>\n            </p>\n        </div>\n        {/if}\n    </li>\n    {/each}\n{/if}\n';
});
define("common/wx/media/idCard.js", [ "tpl/media/id_card.html.js", "xss/ui/media.css", "common/qq/Class.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = wx.T, s = e("tpl/media/id_card.html.js"), o = e("xss/ui/media.css"), u = e("common/qq/Class.js"), a = wx.url("/misc/getheadimg?1=1"), f = u.declare({
            init: function (e) {
                if (!e || !e.container) return;
                e.avatar = a + "&fakeid=" + e.fakeid, $(e.container).html(wx.T(s, e)).data(e), this.opt = e;
            },
            getData: function () {
                return this.opt;
            }
        });
        n.exports = f;
    } catch (l) {
        wx.jslog({
            src: "common/wx/media/idCard.js"
        }, l);
    }
});
define("common/wx/simplePopup.js", [ "tpl/simplePopup.html.js", "common/wx/popup.js", "biz_common/jquery.validate.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = e("tpl/simplePopup.html.js");
        e("common/wx/popup.js"), e("biz_common/jquery.validate.js");
        function s(e) {
            var t = $.Deferred(), n = this;
            n.$dom = $(template.compile(i)(e)).popup({
                title: e.title || "输入提示框",
                buttons: [
                    {
                        text: "确认",
                        click: function () {
                            var i = this;
                            if (r.form()) {
                                var s = n.$dom.find("input").val().trim();
                                if (e.callback) {
                                    var o = e.callback.call(i, s);
                                    o !== !1 && this.remove();
                                } else this.remove();
                                t.resolve(s);
                            }
                        },
                        type: "primary"
                    },
                    {
                        text: "取消",
                        click: function () {
                            this.remove();
                        },
                        type: "default"
                    }
                ],
                className: "simple"
            }), n.$dom.find("input").focus(), $.validator.addMethod("_popupMethod", function (t, n, r) {
                return e && e.rule && e.rule(t.trim(), n, r);
            }, e.msg);
            var r = n.$dom.find("form").validate({
                rules: {
                    popInput: {
                        required: !0,
                        _popupMethod: !0
                    }
                },
                messages: {
                    popInput: {
                        required: "输入框内容不能为空"
                    }
                },
                onfocusout: !1
            });
            return t.callback = t.done, t;
        }

        n.exports = s;
    } catch (o) {
        wx.jslog({
            src: "common/wx/simplePopup.js"
        }, o);
    }
});
define("common/wx/media/appmsg.js", [ "xss/ui/media.css", "common/wx/time.js", "tpl/media/appmsg.html.js", "common/qq/Class.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict", e("xss/ui/media.css");
        var i = wx.T, s = e("common/wx/time.js"), o = e("tpl/media/appmsg.html.js"), u = e("common/qq/Class.js"), a = u.declare({
            init: function (e) {
                if (!e || !e.container) return;
                e.data = e.data || $.extend({}, e);
                var t = e.data, n = t.multi_item || [], r = n.length, i = null, u = [];
                if (r <= 0) return;
                i = n[0];
                for (var a = 1; a < r; ++a) u.push(n[a]);
                var f = {
                    id: t.app_id,
                    type: e.type,
                    file_id: t.file_id,
                    time: t.create_time ? s.timeFormat(t.create_time) : "",
                    isMul: r > 1,
                    first: i,
                    list: u,
                    token: wx.data.t,
                    showEdit: e.showEdit || !1,
                    showMask: e.showMask || !1
                };
                $(e.container).html(wx.T(o, f)).data("opt", e), this.renderData = f;
            },
            getData: function () {
                return this.renderData;
            }
        });
        return a;
    } catch (f) {
        wx.jslog({
            src: "common/wx/media/appmsg.js"
        }, f);
    }
});
define("common/wx/media/video.js", [ "xss/ui/media/richvideo.css", "xss/ui/media.css", "biz_web/lib/video.js", "common/wx/Cgi.js", "common/wx/time.js", "common/qq/Class.js", "biz_web/lib/swfobject.js", "tpl/media/video.html.js", "tpl/media/videomsg.html.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict", e("xss/ui/media/richvideo.css"), e("xss/ui/media.css");
        var i = e("biz_web/lib/video.js"), s = e("common/wx/Cgi.js"), o = e("common/wx/time.js"), u = e("common/qq/Class.js"), a = e("biz_web/lib/swfobject.js"), f = e("tpl/media/video.html.js"), l = e("tpl/media/videomsg.html.js"), c = wx.T, h = wx.data.t, p = document, d = !!a.ua.pv[0], v = p.createElement("video"), m = "#wxVideoBoxFold", g = "#wxVideoPlayer", y = "wxVideo", b = {}, w = navigator.userAgent.toLowerCase(), E = /msie/.test(w), S = /firefox/.test(w);
        i.options.flash.swf = wx.path.video;
        var x = {
            id: "",
            source: "",
            type: "",
            file_id: ""
        }, T = 5e3, N = function (e) {
            if (e.video_url) {
                var t = "tmp" + (Math.random() * 1e5 | 0), n = $('<video id="%s"></video>'.sprintf(t)).appendTo("body");
                i("#" + t).ready(function () {
                    $("#" + t).hide();
                    var n = this;
                    this.on("error", function (t) {
                        n.dispose(), e.dom.find(".loading_tips").show(), e.video_url = "", setTimeout(function () {
                            N(e);
                        }, T);
                    }), this.on("loadedmetadata", function () {
                        n.dispose(), $(e.selector).children().remove(), e.for_transfer = !1, e.digest = e.digest ? e.digest.html(!1) : "", new C(e);
                    });
                    var r = e.video_url;
                    v.canPlayType ? n.src(r) : n.src([
                        {
                            type: "video/x-flv",
                            src: r + "&trans=1"
                        }
                    ]), n.play();
                });
            } else s.get({
                url: wx.url("/cgi-bin/appmsg?action=get_video_url&videoid=%s".sprintf(e.video_id)),
                error: function () {
                    setTimeout(function () {
                        N(e);
                    }, T);
                }
            }, function (t) {
                e.video_url = t.video_url || "", e.video_download_url = t.video_download_url || "", setTimeout(function () {
                    N(e);
                }, T);
            });
        }, C = u.declare({
            init: function (e) {
                var t = this;
                $(e.selector).data("opt", e), e = $.extend(!0, {}, x, e), t.id = e.id, t.source = e.source, t.file_id = e.file_id, t.type = e.type, t.video_url = e.video_url, t.tpl = e.tpl, t.ff_must_flash = e.ff_must_flash, e.src = t.getVideoURL(), e.token = h || wx.data.t, e.time = e.create_time ? o.timeFormat(e.create_time) : "", e.digest = e.digest ? e.digest.replace(/<br.*>/g, "\n").html() : "", e.for_network = typeof e.video_url == "string" ? !e.video_url : !e.content;
                var n = e.tpl == "videomsg" ? $(c(l, e)) : $(c(f, e));
                t.dom = e.dom = $(e.selector).append(n), e.tpl == "videomsg" && e.for_transfer && N(e, t.dom), t.dom.find(".video_desc").length && t.dom.find(".video_desc").html(t.dom.find(".video_desc").attr("data-digest").replace(/\n/g, "<br>")), t.dom.find(".wxVideoScreenshot").on("click", function () {
                    t.dom.find(".mediaContent").css({
                        height: "auto"
                    }), t.play(e.play);
                }), t.dom.find(".wxNetworkVideo").on("click", function () {
                    window.open($(this).attr("data-contenturl"));
                }), t.dom.find(".video_switch").click(function () {
                    t.dom.find(".mediaContent").css({
                        height: "104px"
                    }), t.pause(e.pause);
                });
            },
            getVideoURL: function () {
                var e = this.source, t = this.id, n = this.file_id;
                return !e || (e = "&source=" + e), this.video_url || "/cgi-bin/getvideodata?msgid={msgid}&fileid={fileid}&token={token}{source}".format({
                    msgid: t,
                    fileid: n,
                    source: e,
                    token: wx.data.t
                });
            },
            canPlayType: function () {
                var e = this.type;
                return !v.canPlayType && !d;
            },
            play: function (e) {
                var t = this;
                if (t.canPlayType()) {
                    alert("您当前浏览器无法播放视频，请安装Flash插件/更换Chrome浏览器");
                    return;
                }
                var n = this.id, r = this.player;
                if (!!r) return $("#wxVideoBox" + n).addClass("wxVideoPlaying").find(".wxVideoPlayContent").show(), r.play(), e && e(this);
                var s = t.getVideoURL();
                $("#wxVideoBox" + n).addClass("wxVideoPlaying").find(".wxVideoPlayContent").show();
                var o = t.tpl == "videomsg" ? {
                    width: "100%",
                    height: "100%"
                } : {};
                i("#wxVideo" + n, o).ready(function () {
                    r = this;
                    var i = 0;
                    return r.on("fullscreenchange", function () {
                        i ? ($("#wxVideoPlayer" + n).css({
                            overflow: "hidden",
                            zoom: "1"
                        }), $("#wxVideoBox" + n).css({
                            "z-index": "0"
                        })) : ($("#wxVideoPlayer" + n).css({
                            overflow: "visible",
                            zoom: "normal"
                        }), $("#wxVideoBox" + n).css({
                            "z-index": "1"
                        })), i = ~i;
                    }), r.on("ended", function () {
                        this.currentTime(0);
                    }), E || !v.canPlayType || t.ff_must_flash && S ? r.src([
                        {
                            type: "video/x-flv",
                            src: s + "&trans=1"
                        }
                    ]) : r.src(s), r.play(), t.player = r, e && e(this);
                });
            },
            pause: function (e) {
                var t = this.player;
                t && t.pause(), $("#wxVideoBox" + this.id).removeClass("wxVideoPlaying").find(".wxVideoPlayContent").hide(), e && e(this);
            }
        });
        return C;
    } catch (k) {
        wx.jslog({
            src: "common/wx/media/video.js"
        }, k);
    }
});
define("common/wx/media/img.js", [ "xss/ui/media.css", "tpl/media/img.html.js", "common/qq/Class.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = wx.T, s = e("xss/ui/media.css"), o = e("tpl/media/img.html.js"), u = e("common/qq/Class.js"), a = u.declare({
            init: function (e) {
                if (!e || !e.container) return;
                var t = e;
                $(e.container).html(o.format({
                    token: wx.data.t,
                    id: e.file_id,
                    msgid: e.msgid,
                    source: e.source,
                    ow: ~e.fakeid
                })).data("opt", e), this.data = t;
            },
            getData: function () {
                return this.data;
            }
        });
        return a;
    } catch (f) {
        wx.jslog({
            src: "common/wx/media/img.js"
        }, f);
    }
});
define("common/wx/media/audio.js", [ "biz_web/lib/soundmanager2.js", "tpl/media/audio.html.js", "xss/ui/media.css", "common/qq/Class.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = wx.T, s = e("biz_web/lib/soundmanager2.js"), o = e("tpl/media/audio.html.js"), u = e("xss/ui/media.css"), a = e("common/qq/Class.js"), f = null, l = null, c = null, h = "wxAudioPlaying", p = function () {
            c = s, c.setup({
                url: "/mpres/zh_CN/htmledition/plprecorder/biz_web/",
                preferFlash: !1,
                debugMode: !1
            });
        };
        $(window).load(function () {
            p();
        });
        var d = {
            id: "",
            source: "",
            file_id: ""
        }, v = a.declare({
            init: function (e) {
                var t = this;
                $.extend(!0, t, d, e), this.soundId = this.id || this.file_id, t.play_length = Math.ceil(t.play_length * 1 / 1e3);
                var n = $(i(o, t));
                t.dom = $(e.selector).append(n).data("opt", e), n.click(function () {
                    t.toggle();
                });
            },
            getAudioURL: function () {
                var e = this.source, t = this.id, n = this.file_id;
                return !e || (e = "&source=" + e), wx.url("/cgi-bin/getvoicedata?msgid={id}&fileid={fileid}{source}".format({
                    id: t,
                    fileid: n,
                    source: e
                }));
            },
            isPlaying: function () {
                return f != null && this == f;
            },
            toggle: function () {
                this.isPlaying() ? this.stop() : (f && f.stop(), this.play());
            },
            play: function (e) {
                var t = this;
                if (this.isPlaying()) return;
                this.dom.addClass(h), !!f && f.dom.removeClass(h), f = this, this.sound || (!c && p(), this.sound = c.createSound({
                    id: t.soundId,
                    url: t.getAudioURL(),
                    onfinish: function () {
                        f && (f.dom.removeClass(h), f = null);
                    }
                })), c.play(this.soundId), e && e(this);
            },
            stop: function (e) {
                if (!this.isPlaying()) return;
                f = null, this.dom.removeClass(h), c.stop(this.soundId), e && e(this);
            }
        });
        n.exports = v;
    } catch (m) {
        wx.jslog({
            src: "common/wx/media/audio.js"
        }, m);
    }
});
define("common/wx/media/factory.js", [ "common/wx/media/img.js", "common/wx/media/audio.js", "common/wx/media/video.js", "common/wx/media/appmsg.js", "common/qq/emoji.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = e("common/wx/media/img.js"), s = e("common/wx/media/audio.js"), o = e("common/wx/media/video.js"), u = e("common/wx/media/appmsg.js");
        e("common/qq/emoji.js");
        var a = {
            "1": function (e, t) {
                return $(e).html(t.content.emoji());
            },
            "2": function (e, t) {
                return t.container = $(e), new i(t);
            },
            "3": function (e, t) {
                return t.selector = $(e), t.source = "file", new s(t);
            },
            "4": function (e, t) {
                return t.selector = $(e), t.id = t.file_id, t.source = "file", new o(t);
            },
            "10": function (e, t) {
                return t.container = $(e), t.showMask = !1, new u(t);
            },
            "11": function (e, t) {
                return t.container = $(e), t.showMask = !1, new u(t);
            },
            "15": function (e, t) {
                return t.multi_item && t.multi_item[0] && (t.title = t.multi_item[0].title, t.digest = t.multi_item[0].digest), t.selector = $(e), t.id = Math.random() * 1e6 | 0, t.tpl = "videomsg", t.for_selection = !1, t.for_operation = !1, new o(t);
            }
        }, f = {
            render: function (e, t) {
                a[t.type] && $(e).length > 0 && a[t.type]($(e).html(""), t);
            },
            itemRender: a
        };
        n.exports = f;
    } catch (l) {
        wx.jslog({
            src: "common/wx/media/factory.js"
        }, l);
    }
});
define("media/media_dialog.js", [ "xss/ui/media/media_dialog.css", "xss/ui/media/richvideo.css", "common/wx/popup.js", "common/wx/Tips.js", "media/media_cgi.js", "common/wx/upload.js", "biz_web/ui/checkbox.js", "common/wx/pagebar.js", "common/wx/media/audio.js", "common/wx/media/img.js", "common/wx/media/video.js", "common/wx/media/appmsg.js", "common/wx/time.js", "tpl/media/dialog/file_layout.html.js", "tpl/media/dialog/appmsg_layout.html.js", "tpl/media/dialog/videomsg_layout.html.js", "tpl/media/dialog/file.html.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict", e("xss/ui/media/media_dialog.css"), e("xss/ui/media/richvideo.css"), e("common/wx/popup.js");
        var i = wx.T, s = null, o = null, u = "media", a = e("common/wx/Tips.js"), f = e("media/media_cgi.js"), l = e("common/wx/upload.js"), c = e("biz_web/ui/checkbox.js"), h = e("common/wx/pagebar.js"), p = l.uploadBizFile, d = template.render, v = e("common/wx/media/audio.js"), m = e("common/wx/media/img.js"), g = e("common/wx/media/video.js"), y = e("common/wx/media/appmsg.js"), b = e("common/wx/time.js"), w = b.timeFormat, E = e("tpl/media/dialog/file_layout.html.js"), S = e("tpl/media/dialog/appmsg_layout.html.js"), x = e("tpl/media/dialog/videomsg_layout.html.js"), T = e("tpl/media/dialog/file.html.js"), N = 1, C = {}, k = {
            appmsg: S,
            videomsg: x,
            file: E
        };
        template.helper("mediaDialogtimeFormat", function (e) {
            return w(e);
        }), template.helper("mediaDialogInit", function (e) {
            return e.file_id ? (C[e.file_id] = e, "") : "";
        });
        var L = {
            "2": function (e, t) {
                return new m({
                    container: $("#" + e.attr("id")),
                    file_id: t.file_id,
                    source: "file",
                    fakeid: t.fakeid
                });
            },
            "3": function (e, t) {
                var n = t;
                return n.selector = "#" + e.attr("id"), n.source = "file", new v(n);
            },
            "4": function (e, t) {
                var n = t;
                return n.selector = "#" + e.attr("id"), n.id = n.file_id, n.source = "file", new g(n);
            },
            "15": function (e, t) {
                var n = t;
                return n.selector = e, n.id = n.app_id, n.tpl = "videomsg", n.for_selection = !0, n.for_transfer = !!n.content, n.hide_transfer = !!n.content, n.video_id = n.content, new g(n);
            }
        };

        function A(e, t, n, r, i) {
            var o = e / t + 1, u = new h({
                container: $(".pageNavigator", s.popup("get")),
                perPage: t,
                first: !1,
                last: !1,
                isSimple: !0,
                initShowPage: o,
                totalItemsNum: n,
                callback: function (e) {
                    var n = e.currentPage;
                    if (n == o) return;
                    n--, i.begin = t * n, r(i);
                }
            });
        }

        function O(e, t, n, r, f, l) {
            s && s.popup("remove"), n == 15 && (e = "videomsg");
            var c = N++;
            s = $(i(k[e], {
                tpl: t,
                upload_id: c,
                type: n,
                token: wx.data.t
            }).trim()).popup({
                title: "选择素材",
                className: u,
                width: 960,
                onOK: function () {
                    if (f && !f()) return !0;
                    this.remove(), s = null;
                },
                onCancel: function () {
                    this.remove(), s = null;
                }
            }), o = s.popup("get"), p({
                container: "#js_media_dialog_upload" + c,
                type: n,
                onAllComplete: function () {
                    a.suc("上传成功"), l.begin = 0, M(l);
                }
            });
            if (!!r) {
                e == "file" && ($(".js_media_list", o).html(i(T, {
                    list: r
                })), $(".frm_radio[type=radio]", o).checkbox(!1), $(".media_content", o).each(function () {
                    var e = $(this), t = e.data("id"), n = e.data("type"), r = C[t];
                    if (!r) return;
                    n && L[n] && L[n](e, r);
                }));
                if (e == "appmsg") {
                    var h = r.length, d = $(".js_appmsg_list .inner", o);
                    for (var v = 0; v < h; ++v) {
                        var m = r[v], g = d.eq(v % 2), b = m.app_id, w = $('<div id="appmsg%s"></div>'.sprintf(b), g).appendTo(g);
                        new y({
                            container: w,
                            data: m,
                            showMask: !0
                        });
                    }
                }
                if (e == "videomsg") {
                    var E = o.find("#js_videomsg_list").find(".inner");
                    r.each(function (e, t) {
                        var r = E.eq(t % 2), i = $('<div id="appmsg%s"></div>'.sprintf(e.app_id), r).appendTo(r);
                        L[n] && L[n](i, e);
                    });
                }
                s.popup("resetPosition");
            }
        }

        function M(e) {
            var t = e.type, n = e.onSelect, r = e.count || 10, i = e.begin || 0;
            O("file", "loading"), f.getList(t, i, r, function (u) {
                if (!s) return;
                var a = {
                    "2": "img_cnt",
                    "3": "voice_cnt",
                    "4": "video_cnt"
                }, f = u.file_item, l = u.file_cnt[a[t]];
                f.length <= 0 ? O("file", "no-media", t, f, null, e) : (O("file", "files", t, f, function () {
                    var e = o.find('[name="media"]:checked').val(), r = $("#fileItem" + e).data("opt");
                    return r ? (n(t, r), !0) : !1;
                }, e), A(i, r, l, M, e));
            });
        }

        function _(e) {
            return e.find(".appmsg.selected,.Js_videomsg.selected");
        }

        function D(e) {
            var t = e.type, n = e.onSelect, r = e.count || 10, i = e.begin || 0;
            O("appmsg", "loading"), f.appmsg.getList(t, i, r, function (u) {
                if (!s) return;
                var f = {
                    "10": "app_msg_cnt",
                    "11": "commondity_msg_cnt",
                    "15": "video_msg_cnt"
                }, l = u.item, c = u.file_cnt[f[t]];
                l.length <= 0 ? O("appmsg", "no-media", t, l, null, e) : (O("appmsg", "files", t, l, function () {
                    var e = _(o).parent().data("opt");
                    return e ? (n(t, e), !0) : !1;
                }), A(i, r, c, D, e), t == 15 ? (o.on("click", ".Js_videomsg", function () {
                    $(this).find(".loading_tips").length ? a.err("视频在转码中，不能选择") : $(this).find(".title").text().trim() != "" && (o.find(".Js_videomsg").removeClass("selected"), $(this).addClass("selected"));
                }), o.on("mouseenter", ".Js_videomsg", function () {
                    $(this).find(".title").text().trim() == "" && $(this).addClass("no_title");
                }), o.on("mouseleave", ".Js_videomsg", function () {
                    $(this).removeClass("no_title");
                })) : o.on("click", ".appmsg", function () {
                    _(o).removeClass("selected"), $(this).addClass("selected");
                }));
            });
        }

        return {
            getFile: M,
            getAppmsg: D
        };
    } catch (P) {
        wx.jslog({
            src: "media/media_dialog.js"
        }, P);
    }
});
define("common/wx/richEditor/emotionEditor.js", [ "xss/ui/emotion_editor.css", "tpl/richEditor/emotionEditor.html.js", "common/wx/richEditor/wysiwyg.js", "common/wx/richEditor/emotion.js", "common/wx/upload.js", "common/wx/Tips.js", "common/qq/Class.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = wx.T, s = e("xss/ui/emotion_editor.css"), o = e("tpl/richEditor/emotionEditor.html.js"), u = e("common/wx/richEditor/wysiwyg.js"), a = e("common/wx/richEditor/emotion.js"), f = e("common/wx/upload.js"), l = e("common/wx/Tips.js"), c = f.uploadCdnFile, h = e("common/qq/Class.js"), p = {
            isHTML: !0,
            wordlimit: 500,
            hideUpload: !0
        }, d = 1, v = h.declare({
            init: function (e, t) {
                var n = this;
                t = this.opt = $.extend(!0, {}, p, t), n.selector$ = e, t.gid = d++, n.selector$.html(i(o, t)), n.emotion = new a(e.find(".js_emotionArea")), n.wysiwyg = new u(e.find(".js_editorArea"), {
                    init: function () {
                        e.find(".js_editorTip").html("还可以输入<em>{l}</em>字".format({
                            l: t.wordlimit
                        }));
                    },
                    textFilter: function (e) {
                        return e = n.emotion.getEmotionText(e).replace(/<br.*?>/ig, "\n").replace(/<.*?>/g, ""), e = e.html(!1), t.isHTML ? e : e.replace(/<br.*?>/ig, "\n").replace(/<.*?>/g, "");
                    },
                    nodeFilter: function (e) {
                        var t = "";
                        return e.nodeName.toUpperCase() === "IMG" && (t = e), t;
                    },
                    change: function () {
                        var r = n.getContent(), i = t.wordlimit - r.length, s = e.find(".js_editorTip");
                        i < 0 ? s.html("已超出<em{cls}>{l}</em>字".format({
                            l: -i,
                            cls: ' class="warn"'
                        })) : s.html("还可以输入<em>{l}</em>字".format({
                            l: i
                        }));
                    }
                }), n.upload = c({
                    container: e.find(".js_upload"),
                    type: 2,
                    multi: !1,
                    onComplete: function (e, t, r, i, s) {
                        if (!i || !i.base_resp || i.base_resp.ret != 0) return;
                        var o = i.content;
                        l.suc("上传成功"), n.wysiwyg.insertHTML(o);
                    }
                }), n._initEvent(), n.insertHTML(t.text);
            },
            _initEvent: function () {
                var e = $(".js_switch", this.selector$), t = this.emotion, n = this.wysiwyg;
                t.click(function (e) {
                    return n.insertHTML(t.getEmotionHTML(e)), !1;
                }), t.hide(), e.click(function () {
                    t.show();
                }), $(document).on("click", "*", function (e) {
                    var n = $(e.target), r = n.filter(".js_switch"), i = n.filter(".js_emotion_i"), s = n.filter(".emotions_item");
                    !r.length && !i.length && !s.length && t.hide();
                });
            },
            setContent: function (e) {
                var t = e || "";
                t = this.opt.linebreak ? t.replace(/\n/g, "<br>") : t, e = a.emoji(t), this.wysiwyg.setContent(e, t.html(!1));
            },
            insertHTML: function (e) {
                e = e || "", this.wysiwyg.insertHTML(e);
            },
            getContent: function () {
                var e = this.wysiwyg.getContent();
                return e = typeof e == "string" ? e.trim() : "", this.opt.linebreak ? e.replace(/<br[^>]*>/ig, "\n") : e;
            },
            getHTML: function () {
                var e = this.wysiwyg.getHTML().html(!1);
                return typeof e == "string" ? e.trim() : "";
            }
        });
        n.exports = v;
    } catch (m) {
        wx.jslog({
            src: "common/wx/richEditor/emotionEditor.js"
        }, m);
    }
});
define("common/qq/jquery.plugin/tab.js", [ "tpl/tab.html.js", "xss/ui/msg_tab.css" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = {
            index: 0
        }, s = e("tpl/tab.html.js"), o = e("xss/ui/msg_tab.css");
        $.fn.tab = function (e) {
            if (!e || !e.tabs) return;
            this.html(wx.T(s, {
                tabs: e.tabs
            }));
            var t = this, n = t.find(".tab_navs"), r = n.find(".tab_nav"), o = r.length, u = null, a = null, f = function (n) {
                var r = n.data("tab"), i = n.data("type");
                !r || (u != n && (u && u.removeClass("selected"), a && a.hide(), u = n, a = t.find(r).parent(), a.show(), u.addClass("selected")), !!e.select && e.select(n, a, r, i));
            }, l = function (n) {
                var r = n.data("tab"), i = n.data("type");
                return e.click ? e.click(n, t.find(r), r, i) : !0;
            };
            return e = $.extend(!0, {}, i, e), r.each(function (n) {
                var r = e.index, i = $(this).addClass("width" + o), s = i.data("tab");
                n == r ? f(i) : t.find(s).parent().hide(), n == o - 1 && i.addClass("no_extra");
            }), n.on("click", ".tab_nav", function () {
                var e = l($(this));
                return e != 0 && f($(this));
            }), {
                getLen: function () {
                    return o;
                },
                getTabs: function () {
                    return r;
                },
                select: function (e) {
                    return f(r.eq(e));
                }
            };
        };
    } catch (u) {
        wx.jslog({
            src: "common/qq/jquery.plugin/tab.js"
        }, u);
    }
});
define("common/wx/verifycode.js", [ "widget/verifycode.css", "tpl/verifycode.html.js", "common/qq/events.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict", e("widget/verifycode.css");
        var i = e("tpl/verifycode.html.js"), s = "/cgi-bin/verifycode?r=", o = e("common/qq/events.js"), u = o(!0);

        function a(e) {
            var t = this;
            this.$dom = $(i), this.$img = this.$dom.find("img"), this.$input = this.$dom.find("input"), this.$img.on("load", function () {
                u.trigger("VerifyCode:load", t);
            }), this.$dom.find("a").click(function (e) {
                t.$img.attr("src", s + +(new Date));
            }).click(), e && $(e).append(this.$dom) && (this.$container = $(e));
        }

        a.prototype.getCode = function () {
            return this.$input.val();
        }, a.prototype.focus = function () {
            this.$input.focus();
        }, a.prototype.getInput = function () {
            return this.$input;
        }, a.prototype.refresh = function () {
            this.$img.attr("src", s + +(new Date));
        }, $.fn.verifycode = function () {
            return this.each(function () {
                $.data(this, "verifycode", new a(this));
            });
        }, n.exports = a;
    } catch (f) {
        wx.jslog({
            src: "common/wx/verifycode.js"
        }, f);
    }
});
define("message/renderList.js", [ "common/qq/emoji.js", "common/wx/simplePopup.js", "common/qq/Class.js", "common/wx/media/img.js", "common/wx/media/audio.js", "common/wx/media/video.js", "common/wx/media/idCard.js", "tpl/msgListItem.html.js", "common/wx/RichBuddy.js", "common/wx/remark.js", "common/wx/media/simpleAppmsg.js", "common/qq/events.js", "message/message_cgi.js", "common/wx/time.js", "common/wx/Tips.js", "tpl/message/video_popup.html.js", "common/wx/richEditor/emotionEditor.js", "common/wx/verifycode.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict", e("common/qq/emoji.js");
        var i = e("common/wx/simplePopup.js"), s = e("common/qq/Class.js"), o = e("common/wx/media/img.js"), u = e("common/wx/media/audio.js"), a = e("common/wx/media/video.js"), f = e("common/wx/media/idCard.js"), l = e("tpl/msgListItem.html.js"), c = e("common/wx/RichBuddy.js"), h = e("common/wx/remark.js"), p = e("common/wx/media/simpleAppmsg.js"), d = e("common/qq/events.js"), v = d(!0), m = e("message/message_cgi.js"), g = e("common/wx/time.js"), y = e("common/wx/Tips.js"), b = g.timeFormat, w = e("tpl/message/video_popup.html.js"), E = !1, S = {
            "1": function (e, t) {
                return e.html(t.content.emoji());
            },
            "2": function (e, t) {
                return new o({
                    container: $("#" + e.attr("id")),
                    file_id: 0,
                    msgid: t.id,
                    source: t.source,
                    fakeid: t.fakeid
                });
            },
            "3": function (e, t) {
                var n = t;
                return n.selector = "#" + e.attr("id"), new u(n);
            },
            "4": function (e, t) {
                var n = t;
                return n.selector = "#" + e.attr("id"), n.ff_must_flash = !0, new a(n);
            },
            "42": function (e, t) {
                var n = t;
                return n.container = "#" + e.attr("id"), new f(n);
            },
            "10": function (e, t) {
                var n = t;
                return n.container = "#" + e.attr("id"), new p(n);
            },
            "15": function (e, t) {
                var n = t;
                return n.selector = e, n.tpl = "videomsg", n.id = Math.random() * 1e5 | 0, new a(n);
            }
        };

        function x(e) {
            var t = new c;
            $(".avatar", e).mouseover(function () {
                var e = $(this), n = parseInt(e.attr("data-fakeid"), 10), r = e.offset(), i = e.width();
                if (n == wx.data.uin) return;
                t.show({
                    fakeId: n,
                    position: {
                        left: r.left + i + 2,
                        top: r.top
                    }
                });
            }).mouseout(function () {
                t.hide();
            });
        }

        function T(e) {
            $(".js_changeRemark", e).unbind("click").click(function (t) {
                var n = $(this), r = n.closest("li.msgListItem"), i = n.attr("data-fakeid"), s = $(".nickname[data-fakeid=" + i + "]", e), o = $(".remark_name[data-fakeid=" + i + "]", e), u = $.trim(s.html()) == "" ? "" : o.html();
                h.show(i, u);
            }), v.on("Remark:changed", function (t, n) {
                var r, i, s, o;
                r = $(".nickname[data-fakeid=" + t + "]", e), i = $(".remark_name[data-fakeid=" + t + "]", e), s = $.trim(r.html()) == "" ? "" : i.html(), o = s == "" ? i.html() : r.find("strong").html(), n == "" && s != "" ? (r.html(""), i.html(o)) : n != "" && s == "" ? (i.html(n), r.html("(<strong>{nickName}</strong>)".format({
                    nickName: o
                }))) : n != "" && s != "" && i.html(n);
            });
        }

        function N(e) {
            $(e).off("click", ".js_save").on("click", ".js_save", function (e) {
                var t = $(this), n = t.attr("idx"), r = t.attr("data-type");
                r == 4 ? $(w).popup({
                    title: "保存为视频消息",
                    onOK: function () {
                        var e = this.get().find(".title").val(), t = this.get().find(".digest").val();
                        if (e.length < 1 || e.length > 64) return y.err("请输入1到64个字的标题"), !0;
                        if (t != "" && t.length > 120) return y.err("简介字数不能超过120字"), !0;
                        m.save(n, e, t, function () {
                        });
                    },
                    onCancel: function () {
                        this.hide();
                    },
                    onHide: function () {
                        this.remove();
                    }
                }) : new i({
                    title: "填写素材名字",
                    callback: function (e) {
                        m.save(n, e, function () {
                        });
                    },
                    rule: function (e, t, n) {
                        var r = $.trim(e);
                        return r != "" && r.length <= 50 && r.indexOf(" ") == -1;
                    },
                    msg: "素材名必须为1到50个字符，并且素材名不能包含空格"
                });
            });
        }

        function C(e) {
            e.off("click", ".js_star").on("click", ".js_star", function (e) {
                var t = $(this), n = t.attr("idx"), r = t.attr("action"), i = t.attr("starred");
                m.star(n, i, function (e) {
                    i == 1 ? (t.removeClass("star_orange").addClass("star_gray"), t.attr("starred", 0)) : (t.removeClass("star_gray").addClass("star_orange"), t.attr("starred", 1)), t.attr("title", i == 1 ? "收藏消息" : "取消收藏"), r == "star" && i == 1 && $("#msgListItem" + n).fadeOut();
                });
            });
        }

        var k = e("common/wx/richEditor/emotionEditor.js");

        function L(e) {
            e.off("click", ".js_reply").on("click", ".js_reply", function () {
                var t = $(this), n = t.data("id"), r = $("#msgListItem" + n).toggleClass("replying");
                $(".replying", e).each(function () {
                    var e = $(this), t = e.data("id");
                    t != n && e.removeClass("replying");
                }), r.data("hasClickQuickReply") || (A(r.find(".js_quick_reply_box"), r), r.data("hasClickQuickReply", !0));
            });
        }

        function A(t, n) {
            var r = 140, i = $(".js_editor", t), s = new k(i, {
                wordlimit: r,
                isHTML: !0
            }), o = $(".js_reply_OK", t), u = $(".js_reply_pickup", t);
            u.unbind("click").click(function () {
                var e = $(this).data("id");
                $("#msgListItem" + e).removeClass("replying");
            }), t.keyup(function (e) {
                if (wx.isHotkey(e, "enter")) return o.click(), !1;
            });
            var a = null, f = $(".verifyCode", t), l = e("common/wx/verifycode.js");
            o.unbind("click").click(function () {
                var e = $(this), t = e.data("id"), i = e.data("fakeid"), o = s.getContent();
                if (o.length <= 0 || o.length > r) {
                    y.err("快捷回复的内容必须为1到140个字符");
                    return;
                }
                if (a != null && a.getCode().trim().length <= 0) {
                    y.err("请输入验证码"), a.focus();
                    return;
                }
                y.suc("回复中...请稍候"), e.btn(!1), m.quickReply({
                    toFakeId: i,
                    content: o,
                    quickReplyId: t,
                    imgcode: a && a.getCode().trim()
                }, function (t) {
                    s.setContent(""), f.html("").addClass("dn"), a = null, n.addClass("replyed"), e.btn(!0);
                }, function (t) {
                    e.btn(!0), t && t.base_resp && t.base_resp.ret == -8 && (a = f.html("").removeClass("dn").verifycode().data("verifycode"), a.focus());
                });
            });
        }

        var O = function () {
            $(".avatar img").each(function () {
                var e = $(this);
                !e.data("src") || (e.attr("src", e.data("src")), e.removeAttr("data-src"));
            });
        }, M = function (e) {
            if (!e.list) return;
            var t = e.list, n = {}, r = t.length;
            template.helper("mediaInit", function (e) {
                return e.id ? (n[e.id] = e, "") : "";
            }), template.helper("timeFormat", function (e) {
                return b(e.date_time);
            }), template.helper("id2singleURL", function (e) {
                return wx.url("/cgi-bin/singlesendpage?tofakeid=%s&t=message/send&action=index".sprintf(e.fakeid));
            }), t.each(function (e) {
                e.video_url && (e.type = 15), e.type == 5 && (e.type = 10), e.type == 6 && (e.type = 10), e.type == 11 && (e.type = 10), e.type == 16 && (e.type = 15);
            });
            var i = $(e.container), s = $(wx.T(l, {
                token: wx.data.t,
                list: t,
                uin: wx.data.uin,
                action: e.action
            }).trim());
            e.preAppend ? s.prependTo(i) : s.appendTo(i), E ? O() : (E = !0, $(window).on("load", function () {
                O();
            })), $(".wxMsg", s).each(function () {
                var e = $(this), t = e.data("id"), r = n[t];
                if (!r) return;
                var i = r.type;
                i && S[i] && S[i](e, r);
            }), x(i), T(i), N(i), C(i), L(i);
        };
        return M;
    } catch (_) {
        wx.jslog({
            src: "message/renderList.js"
        }, _);
    }
});
define("message/message_cgi.js", [ "common/wx/Tips.js", "common/wx/Cgi.js" ], function (e, t, n) {
    try {
        var r = +(new Date);
        "use strict";
        var i = {
            masssend: "/cgi-bin/masssend?t=ajax-response",
            massdel: "/cgi-bin/masssendpage?action=delete",
            star: "/cgi-bin/setstarmessage?t=ajax-setstarmessage",
            save: "/cgi-bin/savemsgtofile?t=ajax-response",
            sendMsg: "/cgi-bin/singlesend?t=ajax-response&f=json",
            getNewMsg: "/cgi-bin/singlesendpage?tofakeid=%s&f=json&action=sync&lastmsgfromfakeid=%s&lastmsgid=%s&createtime=%s",
            getNewMsgCount: "/cgi-bin/getnewmsgnum?f=json&t=ajax-getmsgnum&lastmsgid=",
            pageNav: "/cgi-bin/message?f=json&offset=%s&day=%s&keyword=%s&action=%s&frommsgid=%s&count=%s",
            searchMsgByKeyword: "/cgi-bin/getmessage?t=ajax-message&count=10&keyword="
        }, s = e("common/wx/Tips.js"), o = e("common/wx/Cgi.js");
        n.exports = {
            masssend: function (e, t, n) {
                o.post({
                    url: wx.url(i.masssend),
                    dataType: "html",
                    data: e,
                    error: function (e, t) {
                        s.err("发送失败"), n && n();
                    }
                }, function (e) {
                    var r = $.parseJSON(e);
                    if (r.ret && r.ret == "0") {
                        s.suc("发送成功"), t && t(r);
                        return;
                    }
                    r.ret && r.ret == "64004" ? s.err("今天的群发数量已到，无法群发") : r.ret && r.ret == "67008" ? s.err("消息中可能含有具备安全风险的链接，请检查") : r.ret == "-6" ? s.err("请输入验证码") : s.err("发送失败"), n && n(r);
                });
            },
            massdel: function (e, t, n) {
                o.post({
                    url: wx.url(i.massdel),
                    data: {
                        id: e
                    },
                    error: function (e, t) {
                        s.err("删除失败");
                    }
                }, function (e) {
                    if (e && e.base_resp && e.base_resp.ret == 0) {
                        s.suc("删除成功"), t && t(e);
                        return;
                    }
                    s.err("删除失败"), n && n(e);
                });
            },
            getNewMsg: function (e, t, n, r, s, u) {
                o.get({
                    url: wx.url(i.getNewMsg.sprintf(e, t, n, r)),
                    mask: !1,
                    handlerTimeout: !0,
                    error: u
                }, function (e) {
                    e && e.base_resp && e.base_resp.ret == 0 && s && s(e.page_info.msg_items.msg_item);
                });
            },
            save: function (e, t, n, r) {
                typeof n == "function" && (r = n, n = ""), o.post({
                    mask: !1,
                    url: wx.url(i.save),
                    dataType: "html",
                    data: {
                        msgid: e,
                        filename: t,
                        digest: n
                    },
                    error: function (e, t) {
                        s.err("保存素材失败");
                    }
                }, function (e) {
                    var t = $.parseJSON(e);
                    t && t.ret == "0" ? (s.suc("保存素材成功"), typeof r == "function" && r(t)) : s.err("保存素材失败");
                });
            },
            star: function (e, t, n) {
                o.post({
                    mask: !1,
                    url: wx.url(i.star),
                    data: {
                        msgid: e,
                        value: t == 1 ? 0 : 1
                    },
                    dataType: "html",
                    error: function () {
                        s.err(t == 1 ? "取消收藏失败" : "收藏消息失败");
                    }
                }, function (e) {
                    e = $.parseJSON(e), e && e.ret != 0 ? s.err(t == 1 ? "取消收藏失败" : "收藏消息失败") : (s.suc(t == 1 ? "取消收藏成功" : "收藏消息成功"), typeof n == "function" && n(e));
                });
            },
            sendMsg: function (e, t, n) {
                o.post({
                    url: wx.url(i.sendMsg),
                    data: e,
                    error: function () {
                        s.err("发送失败"), n && n();
                    }
                }, function (e) {
                    if (!e || !e.base_resp) {
                        s.err("发送失败");
                        return;
                    }
                    var r = e.base_resp.ret;
                    if (r == 0) {
                        s.suc("回复成功"), typeof t == "function" && t(e);
                        return;
                    }
                    r == 10703 ? s.err("对方关闭了接收消息") : r == 10700 ? s.err("不能发送，对方不是你的粉丝") : r == 10701 ? s.err("该用户已被加入黑名单，无法向其发送消息") : r == 62752 ? s.err("消息中可能含有具备安全风险的链接，请检查") : r == 10704 ? s.err("该素材已被删除") : r == 10705 ? s.err("该素材已被删除") : r == 10706 ? s.err("由于该用户48小时未与你互动，你不能再主动发消息给他。直到用户下次主动发消息给你才可以对其进行回复。") : r == -8 ? s.err("请输入验证码") : s.err("发送失败"), n && n(e);
                });
            },
            getNewMsgCount: function (e, t, n) {
                o.post({
                    mask: !1,
                    dataType: "html",
                    handlerTimeout: !0,
                    url: wx.url(i.getNewMsgCount + e),
                    error: n
                }, function (e) {
                    e = $.parseJSON(e), typeof t == "function" && t(e);
                });
            },
            quickReply: function (e, t, n) {
                this.sendMsg({
                    mask: !1,
                    tofakeid: e.toFakeId,
                    imgcode: e.imgcode,
                    type: 1,
                    content: e.content,
                    quickreplyid: e.quickReplyId
                }, t, n);
            },
            pageNav: function (e, t, n) {
                var r = i.pageNav.sprintf((e.page - 1) * e.count, e.day || "", e.keyword || "", e.action || "", e.frommsgid || "", e.count || "");
                o.post({
                    dataType: "json",
                    url: wx.url(r),
                    mask: !1,
                    data: {},
                    error: n
                }, function (e) {
                    e && e.base_resp && e.base_resp.ret == 0 && typeof t == "function" && t(e.msg_items.msg_item);
                });
            },
            searchMsgByKeyword: function (e, t, n) {
                o.post({
                    dataType: "html",
                    mask: !1,
                    url: wx.url(url.searchMsgByKeyword + e),
                    error: function () {
                        s.err("系统发生异常，请刷新页面重试"), n && n({});
                    }
                }, function (e) {
                    typeof t == "function" && t($.parseJSON(e));
                });
            }
        };
    } catch (u) {
        wx.jslog({
            src: "message/message_cgi.js"
        }, u);
    }
});