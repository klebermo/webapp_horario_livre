<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Instala&ccedil;&atilde;o da aplica&ccedil;&atilde;o</title>
</head>
<body>

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
                                    <form method="post" action="">
                                        <div class="field-box">
                                            <label>M&aacute;quina (IP):</label>
                                            <input class="form-control" type="text" />
                                        </div>
                                        <div class="field-box">
                                            <label>Usu&aacute;rio:</label>
                                            <input class="form-control" type="text" />
                                        </div>
                                        <div class="field-box">
                                            <label>Senha:</label>
                                            <input class="form-control" type="text" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="step-pane" id="step2">
                            <div class="row form-wrapper">
                                <div class="col-md-8">
                                    <form>
                                        <div class="field-box">
                                            <label>Usu&aacute;rio:</label>
                                            <input class="form-control" type="text" />
                                        </div>
                                        <div class="field-box">
                                            <label>Digite uma senha:</label>
                                            <input class="form-control" type="password" />
                                        </div>
                                        <div class="field-box">
                                            <label>Repita a senha:</label>
                                            <input class="form-control" type="password" />
                                        </div>
                                        <div class="field-box">
                                            <label>E-mail:</label>
                                            <input class="form-control" type="text" />
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div class="step-pane" id="step3">
                            <div class="row form-wrapper">
                                <div class="col-md-8">
                                    O sistema foi configurado com sucesso! Agora voc&ecirc; pode efetuar seu login e come&ccedil;ar a usa-lo.
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="wizard-actions">
                        <button type="button" disabled class="btn-glow primary btn-prev"> 
                            <i class="icon-chevron-left"></i> Pr&otilde;ximo
                        </button>
                        <button type="button" class="btn-glow primary btn-next" data-last="Finish">
                            Seguinte <i class="icon-chevron-right"></i>
                        </button>
                        <button type="button" class="btn-glow success btn-finish">
                            Fa&ccedil;a login no sistema!
                        </button>
                    </div>
                </div>
            </div>
        </div>

</body>
</html>