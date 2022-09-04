//
//  ImagePicker.swift
//  AwesomeProject
//
//  Created by thinh nguyen on 04/09/2022.
//
import Foundation

@objc(ImagePicker)

class ImagePicker: NSObject, UIImagePickerControllerDelegate , UINavigationControllerDelegate  {
  @IBOutlet weak var img: UIImageView!
  let controller = RCTPresentedViewController()
  let imageView = UIImageView()
  var imagePicker = UIImagePickerController()
  var callback: RCTResponseSenderBlock?
  
 @objc
  func launchLibrary(_ callback: @escaping RCTResponseSenderBlock) {
    
    DispatchQueue.main.async {
      self.imagePicker.delegate = self
      self.imagePicker.sourceType = .photoLibrary;
      self.imagePicker.allowsEditing = true
      self.controller?.present(self.imagePicker, animated: true)
    }
    
    self.callback = callback;
  }
  
  func imagePickerController(_ picker: UIImagePickerController, didFinishPickingMediaWithInfo info:[UIImagePickerController.InfoKey : Any]) {
  
    let imageURL = info[UIImagePickerController.InfoKey.imageURL] as! URL
    
    /* >>> Base64 >>> */
    let imageData:NSData = NSData.init(contentsOf: imageURL)!
    let strBase64 = imageData.base64EncodedString(options: .lineLength64Characters)
    /* <<< Base64 <<< */
    
    self.callback!([strBase64]);
    controller?.dismiss(animated: true)
  }
  @objc
  static func requiresMainQueueSetup() -> Bool {
    return true;
  }
}
