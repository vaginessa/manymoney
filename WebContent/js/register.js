jQuery(function(){
	// check user nickname by cookie
	// add by sjw at 2013-10-10 18:46
	(function(){
		var nickname = VIPSHOP.cookie.get('VipRNAME'),
			tips = $('div.regHeaderTip'),
			url = document.location.href.toLowerCase();
		if(url.indexOf('/login') !== -1){
			// VIPSHOP浼氬憳
			if(!nickname || nickname === '' || nickname === '\u552f\u54c1\u4f1a\u4f1a\u5458'){
				// 濡傛湁璐﹀彿锛岃鐧诲綍
				tips.html('\u5982\u6709\u8d26\u53f7\uff0c\u8bf7\u767b\u5f55');
			}else{
				nickname = nickname.length > 6 ? nickname.substr(0,6) + '...' : nickname;
				// 锛屾杩庡洖鏉ワ紒璇风櫥褰�
				tips.html('<span data-act="userName" class="black bold">' + nickname + '</span>\uff0c\u6b22\u8fce\u56de\u6765\uff01\u8bf7\u767b\u5f55');
			}
		}else if(url.indexOf('/register') !== -1){
			// VIPSHOP浼氬憳
			if(!nickname || nickname === '' || nickname === '\u552f\u54c1\u4f1a\u4f1a\u5458'){
				tips.html('<span class="fl">娌℃湁璐﹀彿锛熻娉ㄥ唽</span><span class="fr f12">宸叉敞鍐岋紵<a href="/login" class="red hvLine">鐧诲綍</a></span>');			
			}else{
				nickname = nickname.length > 6 ? nickname.substr(0,6) + '...' : nickname;
				tips.html('<span class="fl on">涓嶆槸<span class="black bold" data-act="userName">' + nickname + '</span>锛熻娉ㄥ唽</span><span class="fr f12">宸叉敞鍐岋紵<a href="/login" class="red hvLine">鐧诲綍</a></span>');
			
			}
		}
	})();
	// add end
	var $userName = jQuery("span[data-act='userName']");
	if( $userName.length && $userName.text().length > 6 ){
		$userName.text( $userName.text().substr(0,6) + "..." );
	}

	jQuery(".ipt").focus(function(){
		jQuery(this).addClass("on");
	}).blur(function(){
		jQuery(this).removeClass("on");
	});
	
	jQuery("#guestBtn").click(function(){
		if(VIPSHOP.cookie.get("Guest_ID")){
			redirect2Src();
			return;
		}
		var $btn = jQuery(this);
		$btn.btnLoading('on');	
		$.post((ctx + '/login/guest'),{
            'token' : $btn.attr('token')
        },function(resp){
            if(resp && resp.result){	
                redirect2Src(resp.result.toLowerCase() === 'success' ? resp.data : undefined);
                return;
            }
            alert('鐧诲綍澶辫触锛岃閲嶈瘯');
            window.location.reload();
        });

	});
	jQuery(".changeCode").hover(function(){
		jQuery(this).addClass('on');
	},function(){
		jQuery(this).removeClass('on');
	});
	//input title
	jQuery(".inputs").each(function(){
		var $this = jQuery(this),
			$label = $this.find("label"),
			$input = $this.find("input");
		if( $input.val() && $input.val().length ){
			$label.addClass("hide");
		}
		$input.focus(function(){
			$label.addClass("hide");
		}).blur(function(){
			if( $input.val() && $input.val().length ){
				$label.addClass("hide");
			}else{
				$label.removeClass("hide");
			}
		});
		$input.change(function(){
			$input.removeClass('verified');
		});
		$label.click(function(){
			$input.focus();
		});
	});
	//radio
	//event bind
	jQuery("span.radio").on("myCheck",function(){
		var $this = jQuery(this),
			$name = $this.attr("data-for"),
			$val = $this.attr("data-val"),
			$all = jQuery("span[data-for='" + $name + "']"),
			$i = $this.find("i"),
			$input = jQuery("input[data-type='radio'][name='"+ $name +"']");
		if( !$i.hasClass("checked") ){
			$all.find("i").removeClass("checked");
			$i.addClass("checked");
			$input.val($val);
			$input.iptReset();
		}
	});
	//get default
	jQuery("input[data-type='radio']").each(function(){
		var $this = jQuery(this),
			$name = $this.attr("name"),
			$defVal = $this.val(),
			$radios = jQuery("span.radio[data-for='"+ $name +"']");
			$radios.each(function(){
				var $radio = jQuery(this);
				if( $radio.attr("data-val") == $defVal ){
					$radio.trigger("myCheck");
				}
			});
	});
	//click trigger
	jQuery("span.radio").click(function(){
		jQuery(this).trigger("myCheck");
	});

	//checkbox
	//event bind
	jQuery("span.checkbox").on("myCheck",function(){
		var $this = jQuery(this),
			$name = $this.attr("data-for"),
			$i = $this.find("i"),
			$input = jQuery("input[data-type='checkbox'][name='"+ $name +"']");
		if( $i.hasClass("checked") ){
			$i.removeClass("checked");
			$input.val(0);
		}else{
			$i.addClass("checked");
			$input.val(1);
		}
			
		// for agree
		var logBtn = $('#btnLogin'),
			ali = $('a.alipay');
		if($name.toLowerCase() === 'agree'){
			if($i.hasClass('checked')){
				$('#btnLoginGray').css('display','none');
				$('#btnLogin').show();
				
				$('.linkBox a.alipay').css('background-position','-49px -24px');
				$('.linkBox a.alipay').unbind('click').bind('click',function(e){
					window.open('/login/alipay_account');
				});
			}else{
				$('#btnLoginGray').css('display','block');
				$('#btnLogin').hide();
				
				$('.linkBox a.alipay').css('background-position','-49px 0');
				$('.linkBox a.alipay').removeAttr('onclick');
				$('.linkBox a.alipay').unbind('click').bind('click',function(e){
					e.preventDefault();
					return;
				});
			}
		}
	});
	
	//get default
	jQuery("input[data-type='checkbox']").each(function(){
		var $this = jQuery(this),
			$name = $this.attr("name"),
			$defVal = $this.val() * 1,
			$checkbox = jQuery("span.checkbox[data-for='"+ $name +"']"),
			$i = $checkbox.find("i");
			if ( $defVal ) {
				$i.addClass("checked");
			}else{
				$i.removeClass("checked");
			}
	});
	//click trigger
	jQuery("span.checkbox").click(function(){
		jQuery(this).trigger("myCheck");
	});
	//more link box
	jQuery("a[data-act='showMoreLinks']").click(function(){
		jQuery(".moreLinksBox").toggleClass("hide");
		jQuery(this).toggleClass("on");
		resizeIframe();
	});
	
	
	var $user_login_form = jQuery("#user_login_form");
	
	if($user_login_form.length){
		var $JchangeCode = $user_login_form.find(".changeCode"),
		$J_L_name   = jQuery("#J_L_name"),
		$J_L_psw	= jQuery("#J_L_psw"),
		$J_L_code	= jQuery("#J_L_code"),
		$J_L_vipc 	= jQuery("#J_L_vipc"),
		$btn		= $user_login_form.find(".btnLoginDeep");
		
		ajaxCheckLoginNeedCaptcha();
		
		$JchangeCode.click(function(){
					changeCode(0);
		});
		$user_login_form.find(".verifyCode").click(function(){
			changeCode(0);
		});
		
		$J_L_name.blur(function() {
			if(jQuery(this).hasClass('verified')){
				return false;
			}
			return check_login_name(jQuery(this));
			
		});
		$J_L_psw.blur(function(e) {
			if(jQuery(this).hasClass('verified')){
				return false;
			}
			return check_login_pwd(jQuery(this));
			
		});
		
		$J_L_code.keyup(function(e) {
			if($J_L_code.val().length == 4){
				return check_captcha($J_L_code, $J_L_vipc,0);
			}else{
				$J_L_code.iptReset();
			}
		});
		
		var remLoginName = VIPSHOP.cookie.get("login_username");
		if(remLoginName){
			$J_L_name.val(remLoginName);
			if ($J_L_name.closest('.inputs').length) {
				$J_L_name.siblings('label').addClass('hide');
			};			
			$J_L_name.focus();
		}
		$btn.click(function(){
			if(!check_login_name($J_L_name)
					|| !check_login_pwd($J_L_psw)){
				return false;
			}
			if ('false' == jQuery("#login_code_li").attr("nocheck")) {
				if (!$J_L_code.hasClass('verified') && !check_captcha($J_L_code,$J_L_vipc,0)) {
					return false;
				}
			}
			jQuery(this).btnLoading('on');
			var postdata = $user_login_form.serialize();
			jQuery.post(ctx+'/login?v='+new Date().getTime(),postdata,function(data){
				// 鑾峰彇涓嬬晫闈腑鏄惁鏈塺edirect_url
				var lefengUrl = jQuery("#J_L_redirect_url").val();
				if(typeof(data.result)!='undefined' && data.result == 'haslogin'){
					if(typeof(lefengUrl) == 'undefined' || lefengUrl == ''){	
						if (clsoseMessenger()){
							return false;
						}		
						redirect2Src();
					}else{
						window.location = "https://passport.vip.com/lefeng/redirect?redirectUrl=" + lefengUrl;
					}
					return;
				}
				if(typeof(data.result)!='undefined' && data.result == 'error'){
					show_error({"type":1,"status":data.errorCode});
					$btn.btnLoading('off');
					ajaxCheckLoginNeedCaptcha();
				}else if(typeof(data.result)!='undefined' && data.result == 'success'){
					if(typeof(lefengUrl) == 'undefined' || lefengUrl == ''){				
						callb2c(data.data.signedApiUrl);
					}else{
						window.location = "https://passport.vip.com/lefeng/redirect?redirectUrl=" + lefengUrl;
					}
				}else{
					show_error({"type":1,"status":6});
					$btn.btnLoading('off');
					ajaxCheckLoginNeedCaptcha();
				}
			}
			);
		});
		jQuery(document).keydown(function(e){
			if($user_login_form.length && e.keyCode == 13){
				$user_login_form.find("input").blur();
				$btn.click();   
			}
		});
	}
	
	var $user_reg_form = jQuery("#user_reg_form");
	if ($user_reg_form.length) {
				var $changeCode = $user_reg_form.find(".changeCode"),
					$J_R_name   = jQuery("#J_R_name"),
					$J_R_gender = jQuery("#J_R_gender"),
					$J_R_psw	= jQuery("#J_R_psw"),
					$J_R_pswa	= jQuery("#J_R_pswa"),
					$J_R_code	= jQuery("#J_R_code"),
					$J_R_agree	= jQuery("#J_R_agree"),
					$vipc 		= $user_reg_form.find(':input[name="vipc"]'),
					$btnReg		= $user_reg_form.find(".btnReg");
				$changeCode.click(
						function() {
							changeCode(1);
						});
				$user_reg_form.find(".verifyCode").click(function(){
					changeCode(1);
				});
				changeCode(1);
				
				$('input').blur(function(){
					$('.auth-tips').addClass('z-hide');
				});
				
				$J_R_name.blur(function() {
					if(jQuery(this).hasClass('verified')){
						return false;
					}
					return check_reg_name(jQuery(this));
					
				});
				$J_R_name.focus(function(){
					if(!$J_R_name.val()){
						$J_R_name.tipMsg('璇疯緭鍏ラ偖绠辨垨鑰呮墜鏈哄彿');
					}
				});
				$J_R_psw.blur(function() {
					if(jQuery(this).hasClass('verified')){
						return false;
					}
					return check_reg_pwd(jQuery(this));
				});
				$J_R_psw.focus(function(){
					check_reg_gender($J_R_gender);
					if(!$J_R_psw.val()){
						$J_R_psw.tipMsg('璇疯緭鍏�6-20浣嶅瓧姣嶅拰鏁板瓧鐨勭粍鍚�');
					}
				});
				$J_R_pswa.blur(function() {
					if(jQuery(this).hasClass('verified')){
						return false;
					}
					if(!$J_R_pswa.val()){
						$J_R_pswa.errorMsg('璇疯緭鍏ョ‘璁ゅ瘑鐮�');
						return false;
					}
					return check_reg_pwd(jQuery(this),1);
				});
				$J_R_pswa.focus(function(){
					check_reg_gender($J_R_gender);
				});
				
				$J_R_code.keyup(function(e) {
					if($J_R_code.val().length == 4){
						return check_captcha(jQuery(this), $vipc,1);
					}else{
						$J_R_code.iptReset();
					}
				});
				$btnReg.click(function() {
					if( (!$J_R_name.hasClass('verified') && !check_reg_name($J_R_name))
							||(!check_reg_gender($J_R_gender))
							||(!$J_R_psw.hasClass('verified') && !check_reg_pwd($J_R_psw))
							||!check_reg_pwd($J_R_pswa,1)                                                                                                                              
							||(!$J_R_code.hasClass('verified') && !check_captcha($J_R_code, $vipc,1))
							||!check_agreed($J_R_agree)
							){
							return false;
					}
					
					jQuery(this).btnLoading('on');
					
					jQuery.post(ctx+'/register?v='+new Date().getTime(),$user_reg_form.serialize(),function(data){
						if(typeof(data.result)!='undefined' && data.result == 'haslogin'){
							if (clsoseMessenger()){
								return false;
							}
							redirect2Src();
							return;
						}
						if(typeof(data.result)!='undefined' && data.result == 'error'){
							show_error({"type":2,"status":data.errorCode});
							$btnReg.btnLoading('off');
							changeCode(1);
						}else if(typeof(data.result)!='undefined' && data.result == 'success'){
							callb2c(data.data.signedApiUrl);
						}else{
							show_error({"type":2,"status":10});
							$btnReg.btnLoading('off');
							changeCode(1);
						}
					}
					);
				});
				
				jQuery(document).keydown(function(e){
					if($user_reg_form.length && e.keyCode == 13){
						$user_reg_form.find("input").blur();
						$user_reg_form.find(".btnReg").click();
					}
				});
			}
	if (errorMsg) {
		//show_error(errorMsg);
	}
});

jQuery(function(){	

	var ele,css,
		regTab = $('.u-reg'),
		lgTab = $('.u-lg');
	$('.u-lr-tabs').click(tabClick);
	$('.m-go-reg a').click(function(e){
			tabClick(e,'.u-reg');
	});
	
	function tabClick (e,ele){
		ele = $(ele || e.target);
		css = ele.attr('class');
		css = css.indexOf('u-reg') !== -1 ? 'u-reg' : 'u-lg';
		if(css === 'u-reg'){
			$(".m-lr-title").removeClass("on");
			$('#user_reg_form,.m-go-buy').removeClass('hide');
			$('#for-login,.m-go-reg').addClass('hide');
		}else{
			$(".m-lr-title").addClass("on");
			$('#user_reg_form,.m-go-buy').addClass('hide');
			$('#for-login,.m-go-reg').removeClass('hide');
		}
	}

	//frameLogin tab
	$('.frameTabs a').click(function(){
		var $btns = $('.frameTabs a'),
			$this = $(this),
			_index = $btns.index($this);
		var $mGoBuy = $('.m-go-buy');
		if (_index == 0 && $mGoBuy.length ) {
			$mGoBuy.hide();
			$('.btnCloseFrame').attr('mars_sead','99999|2|2|2');
		}else if( _index == 1 && $mGoBuy.length ){
			$mGoBuy.show();
			$('.btnCloseFrame').attr('mars_sead','99999|2|1|2');
		}
		if (_index == 0 && !$mGoBuy.length ) {
			$('.btnCloseFrame').attr('mars_sead','99999|1|1|2');
		}else if( _index == 1 && !$mGoBuy.length ){
			$('.btnCloseFrame').attr('mars_sead','99999|1|2|2');
		}
		$btns.removeClass('on');
		$this.addClass('on');
		$('.frameTabContent').addClass('hide').eq(_index).removeClass('hide');
		resizeIframe();
	});

	//abteset cid
	var cidTest = function(){
		var $btn = $("[data-act=showMoreLinks]");
		if($btn.length){
			try{
				var cidLetter = document.cookie.match(/mars_cid=\w+(\w);/)[1],
					testTrigger = /[9a-f]/.test(cidLetter);
				if(testTrigger){
					$btn.click();
				}
			}catch(e){}
		}		
	};	
	cidTest();
	resizeIframe();


	//frame login & register seeds
	$('.btnCloseFrame').click(function(){
		if ($.messenger) {
			$.messenger.send('method=closeDialog');
		};
		
	});

	try {
		var seeds;
		if( $.messenger ) {
			if ( !abcType || abcType=='a'  ) {
				seeds = {
					'closeFrame' : '99999|2|1|2',
					'tabLogin' : '99999|2|1|1',
					'tabReg' : '99999|2|2|1',
					'goBuy' : '99999|2|1|3',
					'changeCodeLogin' : '99999|2|1|4',
					'btnLogin' : '99999|2|2|4',
					'btnForgot' : '99999|2|2|5',
					'changeCodeReg' : '99999|2|1|4',
					'btnReg' : '99999|2|1|5',
					'termOfService' : '99999|2|1|6'
				}
			} else {
				seeds = {
					'closeFrame' : '99999|1|2|2',
					'tabLogin' : '99999|1|2|1',
					'tabReg' : '99999|1|1|1',
					'changeCodeLogin' : '99999|1|1|3',
					'btnLogin' : '99999|1|1|4',
					'btnForgot' : '99999|1|1|5',
					'changeCodeReg' : '99999|1|2|3',
					'btnReg' : '99999|1|2|5',
					'termOfService' : '99999|1|2|4'
				}
				$('.frameTabs a').eq(0).click();
			}
		}
		if (seeds) {
			for (var i in seeds){
				if ($('[seed-id='+ i +']').length) {
					$('[seed-id='+ i +']').attr('mars_sead',seeds[i]);
				};
			}
		};
	}catch(e){}
		
});