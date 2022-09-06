//
//  RNShare.m
//  AwesomeProject
//
//  Created by thinh nguyen on 06/09/2022.
//

#import <Foundation/Foundation.h>
#import <React/RCTBridgeModule.h>

@interface RCT_EXTERN_MODULE(ShareContent, NSObject)

RCT_EXTERN_METHOD(share:(NSDictionary *)options)

@end
