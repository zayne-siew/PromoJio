package com.example.promojio.view.scanner;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.promojio.R;
import com.example.promojio.controller.UserService;
import com.example.promojio.model.BrandRenderer;
import com.example.promojio.model.Promo;
import com.example.promojio.view.MainActivity;
import com.example.promojio.view.scanner.dropdown.CategoryAdapter;
import com.google.android.gms.common.api.OptionalModuleApi;
import com.google.android.gms.common.moduleinstall.ModuleInstall;
import com.google.android.gms.common.moduleinstall.ModuleInstallClient;
import com.google.android.gms.common.moduleinstall.ModuleInstallRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tflite.java.TfLite;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScannerFragment extends Fragment {

    private TextInputEditText editTextCompany, editTextPromo, editTextShortDesc, editTextLongDesc;
    private MaterialAutoCompleteTextView dropdownCategory, datePicker;

    private final MaterialDatePicker<Long> materialDatePicker;
    private final ActivityResultLauncher<Intent> cameraLauncher, galleryLauncher;
    private final ActivityResultLauncher<String>
            cameraPermissionLauncher, storagePermissionLauncher;
    private final BarcodeScanner barcodeScanner;

    private final List<String> categories = Arrays.asList("Food", "Shop", "Travel", "Other");
    private final String[] fields = new String[]
            {"Company", "Promo", "Category", "Expiry", "Short Description", "Long Description"};
    private static final String LOG_TAG = "LOGCAT_ScannerFragment";

    private Uri imageUri;

    public ScannerFragment() {
        // Initialise date picker
        MaterialDatePicker.Builder<Long> dateBuilder = MaterialDatePicker.Builder.datePicker();
        dateBuilder.setTitleText("SELECT A DATE");
        this.materialDatePicker = dateBuilder.build();

        // Initialise activity result launchers
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        // imageUri is already taken from camera
                        this.scanImageForQR();
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(
                                getContext(),
                                "Scan cancelled",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    else {
                        Log.e(LOG_TAG, "Error occurred while scanning");
                    }
                }
        );
        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            Log.e(LOG_TAG, "Error occurred while retrieving image");
                            return;
                        }
                        imageUri = data.getData(); // Store chosen image into imageUri
                        this.scanImageForQR();
                    }
                    else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(
                                getContext(),
                                "Import cancelled",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                    else {
                        Log.e(LOG_TAG, "Error occurred while selecting image");
                    }
                }
        );
        cameraPermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        pickImageCamera();
                    }
                    else {
                        Log.d(LOG_TAG, "User denied permission to camera");
                        Toast.makeText(
                                getContext(),
                                "Permission must be granted to use this feature",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );
        storagePermissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestPermission(),
                isGranted -> {
                    if (isGranted) {
                        pickImageGallery();
                    }
                    else {
                        Log.d(LOG_TAG, "User denied permission to write external storage");
                        Toast.makeText(
                                getContext(),
                                "Permission must be granted to use this feature",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }
        );

        // Initialise barcode scanner to scan for QR codes
        BarcodeScannerOptions barcodeScannerOptions = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .enableAllPotentialBarcodes()
                .build();
        barcodeScanner = BarcodeScanning.getClient(barcodeScannerOptions);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_scanner, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialise EditTexts
        editTextCompany = (TextInputEditText) view.findViewById(R.id.editTextCompany);
        editTextPromo = (TextInputEditText) view.findViewById(R.id.editTextPromo);
        editTextShortDesc = (TextInputEditText) view.findViewById(R.id.editTextShortDesc);
        editTextLongDesc = (TextInputEditText) view.findViewById(R.id.editTextLongDesc);

        // Populate category drop-down
        dropdownCategory = (MaterialAutoCompleteTextView) view.findViewById(R.id.dropdownCategory);
        CategoryAdapter categoryAdapter = new CategoryAdapter(
                getActivity(),
                android.R.layout.simple_dropdown_item_1line,
                this.categories
        );
        dropdownCategory.setAdapter(categoryAdapter);

        // Configure date picker
        datePicker = (MaterialAutoCompleteTextView) view.findViewById(R.id.datePicker);
        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> datePicker.setText(materialDatePicker.getHeaderText())
        );
        datePicker.setOnClickListener(v -> this.showDatePicker(v, materialDatePicker));
        datePicker.setOnFocusChangeListener((v, focus) -> {
            if (focus) {
                this.showDatePicker(v, materialDatePicker);
            }
        });

        // Handle buttons
        this.initialiseSubmitButton(view);
        this.initialiseImportQRButton(view);
        this.initialiseScanQRButton(view);
    }

    private void showDatePicker(
            @NonNull View view,
            @NonNull MaterialDatePicker<Long> materialDatePicker
    ) {
        // Hide soft keyboard
        InputMethodManager manager = (InputMethodManager) requireActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        // Show dialog with date picker
        materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
    }

    private void launchCameraPermission() {
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA);
    }

    private void launchStoragePermission() {
        storagePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void pickImageGallery() {
        this.installModules();
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private void pickImageCamera() {
        this.installModules();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "QR Code Scanner");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Camera to scan for QR codes");

        imageUri = requireActivity().getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
        );

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        cameraLauncher.launch(intent);
    }

    private void clearForm() {
        editTextCompany.setText("");
        editTextPromo.setText("");
        dropdownCategory.setText("");
        datePicker.setText("");
        editTextShortDesc.setText("");
        editTextLongDesc.setText("");
    }

    private void scanImageForQR() {
        // Ensure that image containing QR code is defined
        if (this.imageUri == null) {
            Log.e(LOG_TAG, "Image URI has not been initialised");
            return;
        }

        try {
            // Perform scanning on the image
            InputImage inputImage = InputImage.fromFilePath(requireContext(), this.imageUri);
            barcodeScanner.process(inputImage)
                    .addOnSuccessListener(barcodes -> {
                        Log.d(
                                LOG_TAG,
                                "Barcode scanner processed " + barcodes.size() + " barcodes"
                        );
                        for (Barcode barcode : barcodes) {
                            // Treat the QR code as containing text and proceed
                            Log.d(LOG_TAG, "QR Code: "+ barcode.getDisplayValue());
                            Log.d(LOG_TAG, "Barcode raw value: " + barcode.getRawValue());
                            Log.d(LOG_TAG, "Code Type: " + barcode.getValueType());
                            if ((
                                    barcode.getValueType() == Barcode.TYPE_TEXT ||
                                            barcode.getValueType() == Barcode.TYPE_UNKNOWN
                            ) && this.decodeQR(barcode)) {
                                return; // Stop once the first valid QR is found
                            }
                            // Other types of QR codes are not supported
                        }

                        // Handle case where no valid QR code was found
                        Toast.makeText(
                                getContext(),
                                "Failed to scan QR code!",
                                Toast.LENGTH_LONG
                        ).show();
                    })
                    .addOnCanceledListener(() -> {
                        Log.w(LOG_TAG, "Cancelled scanning for QR codes for some reason");
                        Toast.makeText(
                                getContext(),
                                "QR code scanner interrupted unexpectedly",
                                Toast.LENGTH_SHORT
                        ).show();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(LOG_TAG, "Scanning for QR codes failed with exception: " + e);
                        Toast.makeText(
                                getContext(),
                                "Failed to scan QR code!",
                                Toast.LENGTH_LONG
                        ).show();
                    });
        }
        catch (IOException e) {
            Log.e(LOG_TAG, "Scanning threw IOException: " + e);
        }
    }

    private boolean decodeQR(@NonNull Barcode barcode) {
        // Obtain raw value from QR code
        String strPromo = barcode.getRawValue();
        Log.d(LOG_TAG, "QR Information: " + strPromo);
        if (strPromo == null) {
            return false;
        }

        // Parse information in QR code
        String[] promoInfo = new String[this.fields.length];
        try {
            JSONObject jsonPromo = new JSONObject(strPromo);
            for (int i = 0; i < this.fields.length; i++) {
                if (jsonPromo.has(this.fields[i])) {
                    promoInfo[i] = jsonPromo.getString(this.fields[i]);
                }
            }
        }
        catch (JSONException e) {
            Log.i(LOG_TAG, "Unable to parse information from QR code as JSON object");
            return false;
        }

        // Populate form
        this.clearForm();
        editTextCompany.setText(promoInfo[0]);
        editTextPromo.setText(promoInfo[1]);
        dropdownCategory.setText(promoInfo[2]);
        datePicker.setText(promoInfo[3]);
        editTextShortDesc.setText(promoInfo[4]);
        editTextLongDesc.setText(promoInfo[5]);
        return true;
    }

    private void installModules() {
        ModuleInstallClient moduleInstallClient = ModuleInstall.getClient(requireContext());
        OptionalModuleApi optionalModuleApi = TfLite.getClient(requireContext());

        OnFailureListener listener = e ->
                Log.e(LOG_TAG, "Failed to install required modules for scanning");

        moduleInstallClient
                .areModulesAvailable(optionalModuleApi)
                .addOnSuccessListener(response -> {
                    // Check if modules are already present
                    if (response.areModulesAvailable()) {
                        Log.d(LOG_TAG, "Scanning modules are available");
                        return;
                    }

                    // Ensure that the ML Kit API modules are installed before using
                    ModuleInstallRequest moduleInstallRequest = ModuleInstallRequest.newBuilder()
                            .addApi(optionalModuleApi)
                            .build();
                    moduleInstallClient.installModules(moduleInstallRequest)
                            .addOnFailureListener(listener);
                })
                .addOnFailureListener(listener);
    }

    private void initialiseImportQRButton(@NonNull View view) {
        MaterialButton buttonImportQR = (MaterialButton) view.findViewById(R.id.buttonImportQR);
        buttonImportQR.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(
                    v.getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED) {
                // Writing to external storage has been granted; proceed to use gallery
                pickImageGallery();
            }
            else {
                this.launchStoragePermission();
            }
        });
    }

    private void initialiseScanQRButton(@NonNull View view) {
        MaterialButton buttonScanQR = (MaterialButton) view.findViewById(R.id.buttonScanQR);
        buttonScanQR.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(
                    v.getContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_DENIED) {
                this.launchStoragePermission();
                return;
            }
            if (ContextCompat.checkSelfPermission(
                    v.getContext(),
                    Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED) {
                this.launchCameraPermission();
                return;
            }

            // Both required permissions have been granted; proceed with using the camera
            pickImageCamera();
        });
    }

    private void initialiseSubmitButton(@NonNull View view) {
        MaterialButton buttonSubmit = (MaterialButton) view.findViewById(R.id.buttonSubmit);
        buttonSubmit.setOnClickListener(v -> {
            // Retrieve all fields
            String company = editTextCompany.getEditableText().toString(),
                    // promo = editTextPromo.getEditableText().toString(),
                    category = dropdownCategory.getEditableText().toString(),
                    strExpiry = datePicker.getEditableText().toString(),
                    shortDesc = editTextShortDesc.getEditableText().toString(),
                    longDesc = editTextLongDesc.getEditableText().toString();

            // Ensure that required fields are filled
            if (company.isEmpty() || category.isEmpty() ||
                    shortDesc.isEmpty() || longDesc.isEmpty()) {
                Toast.makeText(
                        getContext(),
                        "Please input all required fields",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // Parse user input
            DateFormat formatter = new SimpleDateFormat("d MMM yyyy", Locale.getDefault());
            Date expiry = null;
            try {
                if (!strExpiry.isEmpty()) {
                    expiry = formatter.parse(strExpiry);
                }
            }
            catch (ParseException e) {
                Log.e(LOG_TAG, "Date formatter unable to format date: " + strExpiry);
            }

            // Expecting recognised brands for rendering purposes
            // Reject all other brands
            String formattedCompany = BrandRenderer.getBrandName(company.trim());
            if (formattedCompany.equals(BrandRenderer.ERROR_NAME)) {
                Log.w(LOG_TAG, "Unrecognised brand: " + company);
                Toast.makeText(
                        getContext(),
                        "Sorry, company not recognised",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            /*
             * Expecting 2 kinds of formats:
             *   1. XXX yyy... (where XXX contains a number)
             *      (e.g. 50% off selected items, 1-for-1 product )
             *      Parse as: { bigLabel: XXX, smallLabel: yyy... }
             *   2. Buy XX get YYY free
             *      (e.g. Buy 1 get 1 free)
             *      Parse as: { bigLabel: Buy XX, smallLabel: Get YYY free }
             * Reject all other formats
             */
            String smallLabel, bigLabel;
            shortDesc = shortDesc.trim().replaceAll(" +", " ");
            String[] keywords = shortDesc.split(" ");
            keywords[0] = this.capitalise(keywords[0]);
            if (keywords.length > 1 && keywords[0].matches(".*\\d.*")) {
                bigLabel = keywords[0];
                smallLabel = shortDesc.substring(shortDesc.indexOf(" ") + 1);
            }
            else if (keywords.length >= 5 &&
                     keywords[0].equals("Buy") &&
                     keywords[1].matches(".*\\d.*") &&
                     keywords[2].equalsIgnoreCase("Get") &&
                     keywords[3].matches(".*\\d.*") &&
                     keywords[4].equalsIgnoreCase("Free")) {
                bigLabel = keywords[0] + " " + keywords[1];
                smallLabel = "Get " + keywords[3] + " free";
            }
            else {
                Log.w(LOG_TAG, "Unrecognised short description: " + shortDesc);
                Toast.makeText(
                        getContext(),
                        "Sorry, short description format not recognised",
                        Toast.LENGTH_SHORT
                ).show();
                return;
            }

            // Submit promo code to database for verification and rewarding
            UserService.newInstance().userCreatesPromo(
                    getContext(),
                    response -> {
                        // Notify user of successful addition to promo code
                        Toast.makeText(
                                getContext(),
                                "Promo code registration successful!",
                                Toast.LENGTH_SHORT
                        ).show();
                        ((MainActivity) requireActivity()).notifyTab(R.id.mPromos);
                    },
                    formattedCompany,
                    smallLabel,
                    bigLabel,
                    category,
                    shortDesc,
                    longDesc,
                    expiry == null ? "" : Promo.formatDate(expiry),
                    (int) (Math.random() * 1000)
            );

            // Clear user input
            this.clearForm();
        });
    }

    @NonNull
    private String capitalise(@NonNull String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}