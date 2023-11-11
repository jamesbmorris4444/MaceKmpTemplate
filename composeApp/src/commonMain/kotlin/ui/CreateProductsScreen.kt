package ui

//@OptIn(ExperimentalComposeUiApi::class)
//@Composable
//fun CreateProductsScreen(
//    repository: Repository,
//    navigator: Navigator,
//    configAppBar: (AppBarState) -> Unit,
//    canNavigateBack: Boolean,
//    navigateUp: () -> Unit,
//    openDrawer: () -> Unit,
//    donor: Donor,
//    viewModel: BloodViewModel,
//    onCompleteButtonClicked: () -> Unit,
//) {
//    val screenWidth = configuration.screenWidthDp.dp
//    val leftGridPadding = 20.dp
//    val rightGridPadding = 20.dp
//    val horizontalGridWidth = screenWidth - leftGridPadding - rightGridPadding
//    val horizontalGridHeight = horizontalGridWidth * 2 / 3
//    val gridCellWidth = horizontalGridWidth / 2
//    val gridCellHeight = horizontalGridHeight / 2
//    val enterDinText = Strings.get("enter_din_text")
//    val dinTitle = Strings.get("din_title")
//    val enterProductCodeText = Strings.get("enter_product_code")
//    val productCodeTitle = Strings.get("product_code_title")
//    val enterExpirationText = Strings.get("enter_expiration_text")
//    val expirationTitle = Strings.get("expiration_title")
//    val aboRhTitle = Strings.get("abo_rh_title")
//    val dinText = viewModel.dinTextState.observeAsState().value ?: ""
//    val expirationText  = viewModel.expirationTextState.observeAsState().value ?: ""
//    val productCodeText = viewModel.productCodeTextState.observeAsState().value ?: ""
//    val clearButtonVisible = viewModel.clearButtonVisibleState.observeAsState().value ?: true
//    val confirmButtonVisible = viewModel.confirmButtonVisibleState.observeAsState().value ?: true
//    val confirmNeeded  = viewModel.confirmNeededState.observeAsState().value ?: false
//    val products = viewModel.productsListState.observeAsState().value ?: listOf()
//    val displayedProductList = viewModel.displayedProductListState.observeAsState().value ?: listOf()
//
//    fun processNewProduct() {
//        val product = Product()
//        val productList: MutableList<Product> = products.toMutableList()
//        product.din = dinText
//        product.aboRh = donor.aboRh
//        product.productCode = productCodeText
//        product.expirationDate = expirationText
//        product.donorId = donor.id
//        productList.add(product)
//        viewModel.changeProductsListState(productList)
//    }
//
//    fun addDonorWithProductsToModifiedDatabase() {
//        products.map { product ->
//            product.donorId = donor.id
//        }
//        viewModel.insertDonorAndProductsIntoDatabase(donor, products)
//        viewModel.changeShowStandardModalState(
//            StandardModalArgs(
//                topIconResId = R.drawable.notification,
//                titleText = viewModel.getResources().getString(R.string.made_db_entries_title_text),
//                bodyText = viewModel.getResources().getString(R.string.made_db_entries_body_text),
//                positiveText = viewModel.getResources().getString(R.string.positive_button_text_ok)
//            ) {
//                viewModel.changeShowStandardModalState(StandardModalArgs())
//            }
//        )
//    }
//
//    fun onClearClicked() {
//        viewModel.changeDinTextState("")
//        viewModel.changeProductCodeTextState("")
//        viewModel.changeExpirationTextState("")
//        viewModel.changeClearButtonVisibleState(false)
//        viewModel.changeConfirmButtonVisibleState(false)
//        viewModel.changeConfirmNeededState(false)
//    }
//
//    fun onConfirmClicked() {
//        if (products.isEmpty() && dinText.isEmpty() && productCodeText.isEmpty() && expirationText.isEmpty()) {
//            val dwpList: List<DonorWithProducts> = viewModel.donorsFromFullNameWithProducts(donor.lastName, donor.dob)
//            viewModel.changeDisplayedProductListState(dwpList.flatMap { it.products })
//        } else {
//            viewModel.changeClearButtonVisibleState(true)
//            viewModel.changeConfirmButtonVisibleState(true)
//            viewModel.changeConfirmNeededState(false)
//            processNewProduct()
//            if (displayedProductList.isNotEmpty()) {
//                viewModel.changeDisplayedProductListState(listOf())
//            }
//        }
//    }
//
//    fun onCompleteClicked() {
//        if (confirmNeeded) {
//            viewModel.changeShowStandardModalState(
//                StandardModalArgs(
//                    topIconResId = R.drawable.notification,
//                    titleText = viewModel.getResources().getString(R.string.std_modal_noconfirm_title),
//                    bodyText = viewModel.getResources().getString(R.string.std_modal_noconfirm_body),
//                    positiveText = viewModel.getResources().getString(R.string.positive_button_text_yes),
//                    negativeText = viewModel.getResources().getString(R.string.negative_button_text_no)
//                ) { dismissSelector ->
//                    when (dismissSelector) {
//                        DismissSelector.POSITIVE -> {
//                            processNewProduct()
//                            if (products.isNotEmpty()) {
//                                addDonorWithProductsToModifiedDatabase()
//                            }
//                            onCompleteButtonClicked()
//                        }
//                        else -> { }
//                    }
//                    viewModel.changeShowStandardModalState(StandardModalArgs())
//                }
//            )
//        } else {
//            if (products.isNotEmpty()) {
//                addDonorWithProductsToModifiedDatabase()
//            }
//            onCompleteButtonClicked()
//        }
//    }
//
//    LaunchedEffect(key1 = true) {
//        onComposing(
//            AppBarState(
//                title = createProductsStringName,
//                actions = {
//                    IconButton(onClick = openDrawer) {
//                        Icon(
//                            imageVector = Icons.Filled.Menu,
//                            contentDescription = stringResource(R.string.menu_content_description)
//                        )
//                    }
//                },
//                navigationIcon = {
//                    if (canNavigateBack) {
//                        IconButton(onClick = navigateUp) {
//                            Icon(
//                                imageVector = Icons.Filled.ArrowBack,
//                                contentDescription = stringResource(R.string.back_button_content_description)
//                            )
//                        }
//                    }
//                }
//            )
//        )
//    }
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//        Column(
//            modifier = Modifier.height(40.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text (
//                modifier = Modifier
//                    .padding(PaddingValues(start = leftGridPadding)),
//                text = String.format(stringResource(R.string.create_products_header_text), donor.lastName, donor.firstName)
//            )
//        }
//        LazyVerticalGrid(
//            modifier = Modifier
//                .padding(PaddingValues(start = leftGridPadding, end = rightGridPadding)),
//            columns = GridCells.Fixed(2)
//        ) {
//            item {
//                LazyHorizontalGrid(
//                    modifier = Modifier
//                        .height(horizontalGridHeight),
//                    rows = GridCells.Fixed(2)
//                ) {
//                    item { // upper left
//                        Box(
//                            modifier = Modifier
//                                .size(gridCellWidth, gridCellHeight)
//                                .borders(2.dp, DarkGray, left = true, top = true, bottom = true)
//                        ) {
//                            OutlinedTextField(
//                                modifier = Modifier
//                                    .height(80.dp)
//                                    .padding(PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp))
//                                    .align(Alignment.BottomStart),
//                                value = dinText,
//                                onValueChange = {
//                                    viewModel.changeDinTextState(it)
//                                    viewModel.changeConfirmNeededState(true)
//                                },
//                                shape = RoundedCornerShape(10.dp),
//                                label = { Text(enterDinText) },
//                                singleLine = true,
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//                            )
//                            Text(
//                                modifier = Modifier
//                                    .padding(PaddingValues(start = 8.dp))
//                                    .align(Alignment.TopStart),
//                                text = dinTitle
//                            )
//                        }
//                    }
//                    item { // lower left
//                        Box(
//                            modifier = Modifier
//                                .size(gridCellWidth, gridCellHeight)
//                                .borders(2.dp, DarkGray, left = true, bottom = true)
//                        ) {
//                            OutlinedTextField(
//                                modifier = Modifier
//                                    .height(80.dp)
//                                    .padding(PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp))
//                                    .align(Alignment.BottomStart),
//                                value = productCodeText,
//                                onValueChange = {
//                                    viewModel.changeProductCodeTextState(it)
//                                    viewModel.changeConfirmNeededState(true)
//                                },
//                                shape = RoundedCornerShape(10.dp),
//                                label = { Text(enterProductCodeText) },
//                                singleLine = true,
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//                            )
//                            Text(
//                                modifier = Modifier
//                                    .padding(PaddingValues(start = 8.dp))
//                                    .align(Alignment.TopStart),
//                                text = productCodeTitle
//                            )
//                        }
//                    }
//                }
//            }
//            item {
//                LazyHorizontalGrid(
//                    modifier = Modifier
//                        .height(horizontalGridHeight),
//                    rows = GridCells.Fixed(2)
//                ) {
//                    item { // upper right
//                        Box(
//                            modifier = Modifier
//                                .size(gridCellWidth, gridCellHeight)
//                                .borders(
//                                    2.dp,
//                                    DarkGray,
//                                    left = true,
//                                    top = true,
//                                    right = true,
//                                    bottom = true
//                                )
//                        ) {
//                            Text(
//                                modifier = Modifier
//                                    .padding(PaddingValues(start = 8.dp))
//                                    .align(Alignment.TopStart),
//                                text = aboRhTitle
//                            )
//                            Text(
//                                modifier = Modifier
//                                    .padding(PaddingValues(bottom = 32.dp))
//                                    .align(Alignment.BottomCenter),
//                                text = donor.aboRh
//                            )
//                        }
//                    }
//                    item { // lower right
//                        Box(
//                            modifier = Modifier
//                                .size(gridCellWidth, gridCellHeight)
//                                .borders(2.dp, DarkGray, left = true, right = true, bottom = true)
//                        ) {
//                            OutlinedTextField(
//                                modifier = Modifier
//                                    .height(80.dp)
//                                    .padding(PaddingValues(start = 8.dp, end = 8.dp, bottom = 8.dp))
//                                    .align(Alignment.BottomStart),
//                                value = expirationText,
//                                onValueChange = {
//                                    viewModel.changeExpirationTextState(it)
//                                    viewModel.changeConfirmNeededState(true)
//                                },
//                                shape = RoundedCornerShape(10.dp),
//                                label = { Text(enterExpirationText) },
//                                singleLine = true,
//                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
//                            )
//                            Text(
//                                modifier = Modifier
//                                    .padding(PaddingValues(start = 8.dp))
//                                    .align(Alignment.TopStart),
//                                text = expirationTitle
//                            )
//                        }
//                    }
//                }
//            }
//        }
//        Spacer(modifier = Modifier.padding(top = 16.dp))
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            val keyboardController = LocalSoftwareKeyboardController.current
//            WidgetButton(
//                padding = PaddingValues(start = 8.dp, end = 8.dp),
//                onClick = {
//                    onClearClicked()
//                    keyboardController?.hide()
//                },
//                buttonText = stringResource(R.string.clear_button_text)
//            )
//            WidgetButton(
//                padding = PaddingValues(start = 8.dp, end = 8.dp),
//                onClick = {
//                    onConfirmClicked()
//                    keyboardController?.hide()
//                },
//                buttonText = stringResource(R.string.confirm_button_text)
//            )
//            WidgetButton(
//                padding = PaddingValues(start = 8.dp, end = 8.dp),
//                onClick = {
//                    onCompleteClicked()
//                },
//                buttonText = stringResource(R.string.complete_button_text)
//            )
//        }
//        if (displayedProductList.isEmpty()) {
//            if (products.isNotEmpty()) {
//                Divider(color = colorResource(id = R.color.black), thickness = 2.dp)
//            }
//            ProductListScreen(
//                canScrollVertically = true,
//                productList = products,
//                useOnProductsChange = true,
//                onProductsChange = { viewModel.changeProductsListState(it) },
//                onDinTextChange = { viewModel.changeDinTextState(it) },
//                onProductCodeTextChange = { viewModel.changeProductCodeTextState(it) },
//                onExpirationTextChange = { viewModel.changeDinTextState(it) },
//                enablerForProducts = { true }
//            )
//        } else {
//            if (displayedProductList.isNotEmpty()) {
//                Divider(color = colorResource(id = R.color.black), thickness = 2.dp)
//            }
//            ProductListScreen(
//                canScrollVertically = true,
//                productList = displayedProductList,
//                useOnProductsChange = true,
//                onProductsChange = { viewModel.changeProductsListState(it) },
//                onDinTextChange = { viewModel.changeDinTextState(it) },
//                onProductCodeTextChange = { viewModel.changeProductCodeTextState(it) },
//                onExpirationTextChange = { viewModel.changeDinTextState(it) },
//                enablerForProducts = { true }
//            )
//        }
//
//    }
//}
//
//private fun Modifier.borders(strokeWidth: Dp, color: Color, left: Boolean = false, top: Boolean = false, right: Boolean = false, bottom: Boolean = false) = composed(
//    factory = {
//        val density = LocalDensity.current
//        val strokeWidthPx = density.run { strokeWidth.toPx() }
//
//        Modifier.drawBehind {
//            val width = size.width
//            val height = size.height - strokeWidthPx / 2
//            if (left) {
//                drawLine(
//                    color = color,
//                    start = Offset(x = 0f, y = height),
//                    end = Offset(x = 0f , y = 0f),
//                    strokeWidth = strokeWidthPx
//                )
//            }
//            if (top) {
//                drawLine(
//                    color = color,
//                    start = Offset(x = 0f, y = 0f),
//                    end = Offset(x = width , y = 0f),
//                    strokeWidth = strokeWidthPx
//                )
//            }
//            if (right) {
//                drawLine(
//                    color = color,
//                    start = Offset(x = width, y = 0f),
//                    end = Offset(x = width , y = height),
//                    strokeWidth = strokeWidthPx
//                )
//            }
//            if (bottom) {
//                drawLine(
//                    color = color,
//                    start = Offset(x = width, y = height),
//                    end = Offset(x = 0f , y = height),
//                    strokeWidth = strokeWidthPx
//                )
//            }
//        }
//    }
//)
