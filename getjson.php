<?php
	$con = mysqli_connect("localhost", "id1778517_kolkalgriya", "k0lk4lgr1y4", "id1778517_kolkalgriya");

    $id_trans = $_POST["id_trans"];
    $tgl_trans = $_POST["tgl_trans"];
	$id_pro = $_POST["id_pro"];
	$jumlah = $_POST["jumlah"];
	$id_user = $_POST["id_user"];
	$nama = $_POST["nama"];
	$nohp = $_POST["nohp"];
	$alamat = $_POST["alamat"];
	$email = $_POST["email"];
	$status = $_POST["status"];

	$statement = mysqli_prepare($con, "INSERT INTO transaksi(id_trans, tgl_trans, id_pro, jumlah, id_user, status) VALUES (?,?,?,?,?,?)");
	mysqli_stmt_bind_param($statement, "sssiss", $id_trans, $tgl_trans, $id_pro, $jumlah, $id_user, $status);
	mysqli_stmt_execute($statement);

    $statementcek = mysqli_query($con, "SELECT id_user FROM pembeli WHERE id_user = '$id_user';");

    if(mysqli_num_rows($statementcek) == 0){
    $statementa = mysqli_prepare($con, "INSERT INTO pembeli(id_user, nama, nohp, alamat, email) VALUES (?,?,?,?,?)");
    mysqli_stmt_bind_param($statementa, "sssss", $id_user, $nama, $nohp, $alamat, $email);
    mysqli_stmt_execute($statementa);}

	$response = array();
	$response["success"] = true;

	echo json_encode($response);
?>