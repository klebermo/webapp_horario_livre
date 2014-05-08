<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Instala&ccedil;&atilde;o da aplica&ccedil;&atilde;o</title>

    <!-- Bootstrap core CSS -->
    <link href="<c:out value="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/bootstrap/css/bootstrap-theme.min.css"/>" rel="stylesheet">
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/bootstrap-overrides.css"/>" rel="stylesheet">
    
    <!-- libraries -->
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/uniform.default.css"/>" type="text/css" rel="stylesheet" />
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/select2.css"/>" type="text/css" rel="stylesheet" />
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/bootstrap.datepicker.css"/>" type="text/css" rel="stylesheet" />
    <link href="<c:out value="${pageContext.request.contextPath}/extras/css/font-awesome.css"/>" type="text/css" rel="stylesheet" />

    <!-- global styles -->
    <link rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}/extras/css/layout.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}/extras/css/elements.css"/>" />
    <link rel="stylesheet" type="text/css" href="<c:out value="${pageContext.request.contextPath}/extras/css/icons.css"/>" />
    
    <!-- this page specific styles -->
    <link rel="stylesheet" href="<c:out value="${pageContext.request.contextPath}/extras/css/form-wizard.css"/>" type="text/css" media="screen" />

    <!-- open sans font -->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,600,700,800' rel='stylesheet' type='text/css' />

</head>
<body>

	<!-- main container -->
    <div class="content">

<div id="pad-wrapper">
            <div class="row">
                <div class="col-md-12 col-xs-12">
                    <div id="fuelux-wizard" class="wizard row">
                        <ul class="wizard-steps">
                            <li data-target="#step1" class="active">
                                <span class="step">1</span>
                                <span class="title">Banco de dados</span>
                            </li>
                            <li data-target="#step2">
                                <span class="step">2</span>
                                <span class="title">Dados do administrador</span>
                            </li>
                            <li data-target="#step3">
                                <span class="step">3</span>
                                <span class="title">Conclus&atilde;o</span>
                            </li>
                        </ul>                            
                    </div>
                    <div class="step-content">
                    
                        <div class="step-pane active" id="step1">
                            <div class="row form-wrapper">
                                <div class="col-md-8">
                                    <form name="target" method="post">
                                        <div class="field-box">
                                            <label>M&aacute;quina (IP):</label>
                                            <input class="form-control" type="text" name="maquina" />
                                        </div>
                                        <div class="field-box">
                                            <label>Usu&aacute;rio:</label>
                                            <input class="form-control" type="text" name="usuario_db" />
                                        </div>
                                        <div class="field-box">
                                            <label>Senha:</label>
                                            <input class="form-control" type="text" name="senha_db" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        
                        <div class="step-pane" id="step2">
                            <div class="row form-wrapper">
                                <div class="col-md-8">
                                    <form name="target" method="post">
                                        <div class="field-box">
                                            <label>Usu&aacute;rio:</label>
                                            <input class="form-control" type="text" name="usuario" />
                                        </div>
                                        <div class="field-box">
                                            <label>Digite uma senha:</label>
                                            <input class="form-control" type="password" name="senha1" />
                                        </div>
                                        <div class="field-box">
                                            <label>Repita a senha:</label>
                                            <input class="form-control" type="password" name="senha2" />
                                        </div>
                                        <div class="field-box">
                                            <label>Primeiro nome:</label>
                                            <input class="form-control" type="text" name="primeiroNome" />
                                        </div>
                                        <div class="field-box">
                                            <label>Ultimo nome:</label>
                                            <input class="form-control" type="text" name="ultimoNome" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        
                        <div class="step-pane" id="step3">
                            <div class="row form-wrapper payment-info">
                                <div class="col-md-8">
                                    O sistema foi configurado com sucesso! Agora voc&ecirc; pode efetuar seu login e come&ccedil;ar a usa-lo.
                                </div>
                            </div>
                        </div>
                        
                    </div>
                    
                    <div class="wizard-actions">
                        <button type="button" disabled class="btn-glow primary btn-prev"> 
                            <i class="icon-chevron-left"></i> Anterior
                        </button>
                        <button type="submit" class="btn-glow primary btn-next" data-last="Finish">
                            Pr&otilde;ximo <i class="icon-chevron-right"></i>
                        </button>
                        <button type="button" onclick="<c:out value="${pageContext.request.contextPath}/acesso/login"/>" class="btn-glow success btn-finish">
                            Fa&ccedil;a login no sistema!
                        </button>
                    </div>
                    
                    <div class="alert alert-danger" id="result">
                        <i class="icon-remove-sign"></i>
                    </div>
                        
                </div>
            </div>
        </div>
    </div>
    <!-- end main container -->

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="<c:out value="${pageContext.request.contextPath}/jquery/js/jquery-2.1.0.min.js"/>"></script>
    <script src="<c:out value="${pageContext.request.contextPath}/jquery/js/jquery-ui-1.10.4.custom.min.js"/>"></script>
    <script src="<c:out value="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"/>"></script>
    <script src="<c:out value="${pageContext.request.contextPath}/extras/js/jquery-ui-timepicker-addon.js"/>"></script>
    <script src="<c:out value="${pageContext.request.contextPath}/extras/js/jquery-ui-sliderAccess.js"/>"></script>
    <script src="<c:out value="${pageContext.request.contextPath}/extras/js/fuelux.wizard.js"/>"></script>

    <script type="text/javascript">
        $(function () {
        	$( "#result" ).hide();
        	
            var $wizard = $('#fuelux-wizard'),
                $btnPrev = $('.wizard-actions .btn-prev'),
                $btnNext = $('.wizard-actions .btn-next'),
                $btnFinish = $(".wizard-actions .btn-finish");

            $wizard.wizard().on('finished', function(e) {
                // wizard complete code
            }).on("changed", function(e) {
                var step = $wizard.wizard("selectedItem");
                // reset states
                $btnNext.removeAttr("disabled");
                $btnPrev.removeAttr("disabled");
                $btnNext.show();
                $btnFinish.hide();

                if (step.step === 1) {
                    $btnPrev.attr("disabled", "disabled");
                    $btnPrev.attr("formaction","<c:out value="${pageContext.request.contextPath}/instala/createdb"/>");
                } else if(step.step === 2) {
                	$btnPrev.attr("formaction","<c:out value="${pageContext.request.contextPath}/instala/createuser"/>");
                } else if (step.step === 3) {
                    $btnNext.hide();
                    $btnFinish.show();
                };
            });

            $btnPrev.on('click', function() {
                $wizard.wizard('previous');
            });
            
            $btnNext.on('click', function() {
                $wizard.wizard('next');
            });
            
            $( "#target" ).submit(function( event ) {
           	 
          	  // Stop form from submitting normally
          	  event.preventDefault();
          	 
          	  // Get some values from elements on the page:
          	  var $form = $( this ),
          	  	url = $form.attr( "action" );
          	 
          	  // Send the data using post
          	  var posting = $.post( url, $(this).serialize() );
          	 
          	  // Put the results in a div
          	  posting.done(function( data ) {
          		  if(data == "not") {
          			$( "#result" ).show();
          			$( "#result" ).empty().append( "Erro ao executar os procedimentos dessa etapa" ).fadeOut();
          		  }
          	  });
          	});
        });
    </script>

</body>
</html>