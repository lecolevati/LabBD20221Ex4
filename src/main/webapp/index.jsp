<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./css/styles.css">
<meta charset="ISO-8859-1">
<title>Exercício Proc CPF</title>
</head>
<body>
	<div align="center" class="container">
		<form action="cliente" method="post">
			<p class="title">
				<b>Pessoa</b>
			</p>
			<table>
				<tr>
					<td colspan="3">
						<input class="cpf_input_data" type="number" 
							id="cpf" name="cpf" placeholder="CPF"
							value='<c:out value="${cliente.cpf }"></c:out>'>
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Consultar">
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text" id="nome" name="nome"
							placeholder="Nome"
							value='<c:out value="${cliente.nome }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="text" id="email" name="email"
							placeholder="E-Mail"
							value='<c:out value="${cliente.email }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						<input class="input_data" type="number" id="limite" name="limite"
							min="0" step="0.01" placeholder="Limite de Crédito"
							value='<c:out value="${cliente.limite }"></c:out>'>
					</td>
				</tr>				
				<tr>
					<td colspan="4">
						<input class="input_data" type="date" id="dtNasc" name="dtNasc"
							placeholder="Data Nascimento"
							value='<c:out value="${cliente.dtNasc }"></c:out>'>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" id="botao" name="botao" value="Inserir">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Atualizar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Deletar">
					</td>
					<td>
						<input type="submit" id="botao" name="botao" value="Listar">
					</td>
				</tr>
			</table>
		</form>		
	</div>
	<div align="center">
		<c:if test="${not empty erro }">
			<H2><c:out value="${erro }" /></H2>
		</c:if>
	</div>
	<div align="center">
		<c:if test="${not empty saida }">
			<H3><c:out value="${saida }" /></H3>
		</c:if>
	</div>
	<br />
	<br />
	<div align="center">
		<c:if test="${not empty clientes }">
			<table class="table_round">
				<thead>
					<tr>
						<th>CPF</th>
						<th>Nome</th>
						<th>E-Mail</th>
						<th>Limite Crédito</th>
						<th>Data Nascimento</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${clientes }">
					<tr>
						<td><c:out value="${c.cpf }"/></td>
						<td><c:out value="${c.nome }"/></td>
						<td><c:out value="${c.email }"/></td>
						<td><c:out value="${c.limite }"/></td>
						<td><c:out value="${c.dtNasc }"/></td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>